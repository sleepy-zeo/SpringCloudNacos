package com.lullaby.ssjr.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.base64url.internal.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtUtil {

    private static PublicKey publicKey;
    private static PrivateKey privateKey;

    private static String accessTokenExpireTime;

    @Value("${accessTokenExpireTime}")
    public void setAccessTokenExpireTime(String accessTokenExpireTime) {
        JwtUtil.accessTokenExpireTime = accessTokenExpireTime;
    }

    static {
        try {
            publicKey = getPublicKey(getPEMPublicKeyString());
            privateKey = getPrivateKey(getPrivateKeyString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static PublicKey getPublicKey(String publicKeyBase64) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String pem = publicKeyBase64
                .replaceAll("\\-*BEGIN.*KEY\\-*", "")
                .replaceAll("\\-*END.*KEY\\-*", "");
        Security.addProvider(new BouncyCastleProvider());
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(pem));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(pubKeySpec);
        return publicKey;
    }

    private static PrivateKey getPrivateKey(String privateKeyBase64) {
        String privKeyPEM = privateKeyBase64
                .replaceAll("\\-*BEGIN.*KEY\\-*", "")
                .replaceAll("\\-*END.*KEY\\-*", "");
        // Base64 decode the data
        byte[] encoded = Base64.decodeBase64(privKeyPEM);
        try {
            DerInputStream derReader = new DerInputStream(encoded);
            DerValue[] seq = derReader.getSequence(0);

            if (seq.length < 9) {
                throw new GeneralSecurityException("Could not read private key");
            }
            // skip version seq[0];
            BigInteger modulus = seq[1].getBigInteger();
            BigInteger publicExp = seq[2].getBigInteger();
            BigInteger privateExp = seq[3].getBigInteger();
            BigInteger primeP = seq[4].getBigInteger();
            BigInteger primeQ = seq[5].getBigInteger();
            BigInteger expP = seq[6].getBigInteger();
            BigInteger expQ = seq[7].getBigInteger();
            BigInteger crtCoeff = seq[8].getBigInteger();
            RSAPrivateCrtKeySpec keySpec = new RSAPrivateCrtKeySpec(modulus, publicExp, privateExp,
                    primeP, primeQ, expP, expQ, crtCoeff);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            return factory.generatePrivate(keySpec);
        } catch (IOException | GeneralSecurityException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private static String getPEMPublicKeyString() throws IOException {
        Resource resource = new ClassPathResource("pem/rsa_public_key.pem");
        return IOUtils.toString(new FileInputStream(resource.getFile()));
    }

    private static String getPrivateKeyString() throws IOException {
        Resource resource = new ClassPathResource("pem/rsa_private_key.pem");
        return IOUtils.toString(new FileInputStream(resource.getFile()));
    }

    public static String generateToken(String account) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + Long.parseLong(accessTokenExpireTime) * 1000);

        Map<String, Object> claims = new HashMap<>();
        claims.put("account", account);

        return Jwts.builder()
                .setClaims(claims)
                .setHeaderParam("typ", "JWT")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, privateKey)
                .compact();
    }

    public static Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(publicKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("validate is token error ", e);
            return null;
        }
    }

    public static boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}
