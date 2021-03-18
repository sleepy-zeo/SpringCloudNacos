package com.sleepy.zeo.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jose4j.base64url.internal.apache.commons.codec.binary.Base64;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;
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
import java.util.Map;
import java.util.UUID;

@Slf4j
public class JwtManager {

    private static PublicKey publicKey;
    private static PrivateKey privateKey;

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
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
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
        byte[] encoded = org.jose4j.base64url.internal.apache.commons.codec.binary.Base64.decodeBase64(privKeyPEM);
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
            log.error("whw", e);
        }
        return null;
    }

    //获取本地公钥文件*.pem
    private static String getPEMPublicKeyString() throws IOException {
        Resource resource = new ClassPathResource("pem/rsa_public_key.pem");
        return IOUtils.toString(new FileInputStream(resource.getFile()));
    }

    //获取本地私钥文件*.pem
    private static String getPrivateKeyString() throws IOException {
        Resource resource = new ClassPathResource("pem/rsa_private_key.pem");
        return IOUtils.toString(new FileInputStream(resource.getFile()));
    }

    // jose4j
    public String createTokenByJose4J(Map<String, Object> map) throws JoseException {
        final JwtClaims claims = new JwtClaims();
        if (map != null) {
            for (String s : map.keySet()) {
                claims.setClaim(s, map.get(s));
            }
        }
        claims.setExpirationTime(NumericDate.fromMilliseconds(System.currentTimeMillis() + 10 * 60 * 1000));
        claims.setIssuer("scn-issuer");
        claims.setAudience("scn-audience");
        claims.setIssuedAtToNow();
        claims.setNotBefore(NumericDate.now());

        // Generate the payload
        final JsonWebSignature jws = new JsonWebSignature();
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
        jws.setPayload(claims.toJson());
        jws.setKeyIdHeaderValue(UUID.randomUUID().toString());
        // Sign using the private key
        jws.setKey(privateKey);

        return jws.getCompactSerialization();
    }

    // jose4j
    public JwtClaims verifyTokenByJose4J(String token) throws InvalidJwtException {
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setVerificationKey(publicKey)
                .setExpectedIssuer("scn-issuer")
                .setExpectedAudience("scn-audience")
                .build();

        return jwtConsumer.processToClaims(token);
    }

    // jjwt
    public String createTokenByJjwt(Map<String, Object> map, String subject) {
        Date now = new Date();
        return Jwts
                .builder()
                .setSubject(subject)
                .addClaims(map)
                .setExpiration(new Date(now.getTime() + 60 * 1000))
                .setIssuer("scn-issuer")
                .setNotBefore(now)
                .setIssuedAt(now)
                .setAudience("scn-audience")
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    // jjwt
    public Claims verifyTokenByJjwt(String jwt) {
        return Jwts
                .parser()
                .setSigningKey(publicKey)
                .requireIssuer("scn-issuer")
                .requireAudience("scn-audience")
                .parseClaimsJws(jwt)
                .getBody();
    }
}
