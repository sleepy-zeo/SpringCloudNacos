package com.lullaby.auth.config;

import com.lullaby.auth.realm.UserDetailsRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
public class ShiroConfig {

    // 无需认证即可访问
    private static final String SHIRO_ANONYMOUS = "anon";
    // 必须认证才可访问
    private static final String SHIRO_AUTHENTICATION = "authc";
    // 必须拥有记住我功能才能访问
    private static final String SHIRO_USER = "user";
    // 拥有对某个资源的权限才能访问
    private static final String SHIRO_PERMISSIONS = "perms";
    // 拥有某个角色权限才能访问
    private static final String SHIRO_ROLES = "roles";

    private static final String LOGIN_URL = "/login";
    private static final String NO_AUTH_URL = "/unauth";
    private static final String SUCCESSFUL_URL = "/main";

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 配置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 配置shiro内置过滤器，第一个值为要拦截的地址，第二个值是要使用的过滤器
        // 必须是LinkedHashMap，因为需要保证有序性
        Map<String, String> fileMap = new LinkedHashMap<String, String>();
        fileMap.put("/anonymous", SHIRO_ANONYMOUS);
        fileMap.put("/auth/**", SHIRO_AUTHENTICATION);
        fileMap.put("/api/**", SHIRO_PERMISSIONS + "[api:select]");
        fileMap.put("/operate/**", SHIRO_ROLES + "[admin]");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(fileMap);

        shiroFilterFactoryBean.setLoginUrl(LOGIN_URL);
        shiroFilterFactoryBean.setSuccessUrl(SUCCESSFUL_URL);
        shiroFilterFactoryBean.setUnauthorizedUrl(NO_AUTH_URL);

        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(UserDetailsRealm userDetailsRealm) {
        log.info("加载DefaultWebSecurityManager");
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(userDetailsRealm);
        return defaultWebSecurityManager;
    }

}
