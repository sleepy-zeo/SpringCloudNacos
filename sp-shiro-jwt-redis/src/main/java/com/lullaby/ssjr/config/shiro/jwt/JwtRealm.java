package com.lullaby.ssjr.config.shiro.jwt;

import com.lullaby.ssjr.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class JwtRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;

        String jwt = (String) jwtToken.getCredentials();

        Claims claims = JwtUtil.getClaimByToken(jwt);
        if (claims == null) {
            throw new UnknownAccountException();
        }

        return new SimpleAuthenticationInfo("whw", jwt, getName());
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