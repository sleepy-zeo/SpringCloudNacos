package com.lullaby.ssjr.config.shiro.jwt;

import com.lullaby.ssjr.common.Constants;
import com.lullaby.ssjr.common.entity.User;
import com.lullaby.ssjr.config.redis.JedisTemplate;
import com.lullaby.ssjr.utils.JwtUtil;
import com.lullaby.ssjr.utils.UUIDUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class JwtRealm extends AuthorizingRealm {

    @Autowired
    private JedisTemplate jedisTemplate;
    @Value("${shiro.cacheTokenExpireTime}")
    private String cacheTokenExpireTime;
    @Value("${shiro.refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // JwtAuthenticationFilter中login传入的token
        JwtToken jwtToken = (JwtToken) token;

        String jwt = (String) jwtToken.getCredentials();

        Claims claims = JwtUtil.getClaimByToken(jwt);
        if (claims == null) {
            throw new UnknownAccountException();
        }
        String accessKey = (String) claims.get(Constants.JWT_CLAIMS_KEY_ACCESS_KEY);
        log.info("doGetAuthenticationInfo, accessKey: " + accessKey);

        if (jedisTemplate.exists(Constants.PREFIX_SHIRO_CACHE + accessKey)) {
            // redis中存在缓存的认证信息
            jedisTemplate.setObject(Constants.PREFIX_SHIRO_CACHE + accessKey, accessKey, Integer.parseInt(cacheTokenExpireTime));
            jedisTemplate.setObject(Constants.PREFIX_SHIRO_REFRESH_TOKEN + accessKey, accessKey, Integer.parseInt(refreshTokenExpireTime));
            return new SimpleAuthenticationInfo(jwt, jwt, getName());
        } else if (jedisTemplate.exists(Constants.PREFIX_SHIRO_REFRESH_TOKEN + accessKey)) {
            // redis中不存在缓存的认证信息，但是存在refreshToken信息
            jedisTemplate.setObject(Constants.PREFIX_SHIRO_CACHE + accessKey, accessKey, Integer.parseInt(cacheTokenExpireTime));
            jedisTemplate.setObject(Constants.PREFIX_SHIRO_REFRESH_TOKEN + accessKey, accessKey, Integer.parseInt(refreshTokenExpireTime));
            return new SimpleAuthenticationInfo(jwt, jwt, getName());
        } else {
            // redis中没有任何相关信息

            // 模拟从数据库中查询
            String account = accessKey.substring(0, accessKey.length() - UUIDUtil.uuidLength());
            User userTemp = new User();
            userTemp.setAccount(account);
            userTemp.setPassword("Rjk2MDVEM0E2NEE1NDFGN0U5OEI4NDU0NUFENDVBOUNCRkExOTc3RTgzNDQ5NTM2NEI0MjkxMDc2NkZBRTNFNQ==");
            if (userTemp == null) {
                throw new AuthenticationException("该帐号不存在(The account does not exist.)");
            }
            jedisTemplate.setObject(Constants.PREFIX_SHIRO_CACHE + accessKey, accessKey, Integer.parseInt(cacheTokenExpireTime));
            jedisTemplate.setObject(Constants.PREFIX_SHIRO_REFRESH_TOKEN + accessKey, accessKey, Integer.parseInt(refreshTokenExpireTime));
            return new SimpleAuthenticationInfo(jwt, jwt, getName());
        }
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        // principalCollection.getPrimaryPrincipal()即claims.getSubject()
        if (principalCollection == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        Set<String> roleSet = new HashSet<>();
        roleSet.add("admin");
        roleSet.add("user");
        authorizationInfo.setRoles(roleSet);
        Set<String> permsSet = new HashSet<>();
        permsSet.add("read");
        permsSet.add("write");
        authorizationInfo.setStringPermissions(permsSet);

        return authorizationInfo;
    }
}