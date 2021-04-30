package com.lullaby.js.config;

import com.lullaby.js.shiro.JwtAuthenticationFilter;
import com.lullaby.js.shiro.JwtAuthorizationFilter;
import com.lullaby.js.shiro.JwtRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
public class ShiroConfig {

    static class JwtDefaultWebSubjectFactory extends DefaultWebSubjectFactory {

        @Override
        public Subject createSubject(SubjectContext context) {
            // 关闭shiro自带的getSession方法
            context.setSessionCreationEnabled(false);
            return super.createSubject(context);
        }
    }

    @Bean
    public Realm jwtRealm() {
        return new JwtRealm();
    }

    @Bean
    public CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    @Bean
    public DefaultWebSubjectFactory subjectFactory() {
        return new JwtDefaultWebSubjectFactory();
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 设置cacheManager
        securityManager.setCacheManager(cacheManager());
        // 设置realm
        securityManager.setRealm(jwtRealm());
        // 禁止Subject的getSession方法
        securityManager.setSubjectFactory(subjectFactory());
        // 关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    // ========================== 过滤器 ==========================
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 资源文件
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/*.ico", "anon");
        filterChainDefinitionMap.put("/static/js/**", "anon");
        filterChainDefinitionMap.put("/static/css/**", "anon");
        filterChainDefinitionMap.put("/static/fonts/**", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        // 接口文档
        filterChainDefinitionMap.put("/doc.html", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources", "anon");
        filterChainDefinitionMap.put("/api-docs", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");
        filterChainDefinitionMap.put("/swagger.json", "anon");
        // 登录退出
        filterChainDefinitionMap.put("/**/login", "anon");
        filterChainDefinitionMap.put("/**/logout", "anon");
        // 测试接口
        filterChainDefinitionMap.put("/api/test/updateMobile", "anon");
        // 剩下的所有都需要认证
        filterChainDefinitionMap.put("/**", "jwtAuthentication, jwtAuthorization");
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);

        //自定义过滤器
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwtAuthentication", new JwtAuthenticationFilter());
        filterMap.put("jwtAuthorization", new JwtAuthorizationFilter());
        shiroFilter.setFilters(filterMap);

        return shiroFilter;
    }
    // ========================== 过滤器 ==========================

    // Shiro生命周期处理器
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
