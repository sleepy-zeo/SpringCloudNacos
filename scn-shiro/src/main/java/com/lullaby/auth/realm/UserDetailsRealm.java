package com.lullaby.auth.realm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

/**
 *
 * Realm中主要的工作就是依据传过来的principal从数据库中获取用户的认证信息和授权信息
 *
 * 建议每个自定义Realm支持一种token，比如这里的UserDetailsRealm只支持UserDetailsToken
 */
@Slf4j
@Component
public class UserDetailsRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UserDetailsToken;
    }

    /**
     * 认证的操作
     *
     * 通过principal从数据库中获取credentials
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("doGetAuthenticationInfo");

        UserDetailsToken authentication = (UserDetailsToken) authenticationToken;
        String username = authentication.getUsername();
        // 根据username获取到数据库中的password
        String dbPassword = "1994";

        return new SimpleAuthenticationInfo(username, dbPassword, UserDetailsRealm.class.getSimpleName());
    }

    /**
     * 授权的操作
     *
     * 通过principal从数据库中获取相关角色+权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("doGetAuthorizationInfo");

        // 一个主体有多个principals，比如用户名/手机号/邮箱，但只有一个primary principal
        String username = (String) principalCollection.getPrimaryPrincipal();
        // 根据username获取到数据库中的roles和permissions
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole("admin");
        simpleAuthorizationInfo.addStringPermission("api:select");

        return simpleAuthorizationInfo;
    }
}
