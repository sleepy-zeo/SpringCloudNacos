package com.lullaby.ssjr.config.shiro;

import com.lullaby.ssjr.config.redis.JedisTemplate;
import com.lullaby.ssjr.config.shiro.cache.JedisCacheManager;
import com.lullaby.ssjr.config.shiro.jwt.JwtAuthenticationFilter;
import com.lullaby.ssjr.config.shiro.jwt.JwtRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
public class ShiroConfig {

    @Bean
    public DefaultWebSecurityManager securityManager(JwtRealm jwtRealm, JedisTemplate jedisTemplate) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 设置cacheManager
        securityManager.setCacheManager(new JedisCacheManager(jedisTemplate));
        // 设置realm
        securityManager.setRealm(jwtRealm);
        // 关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager, JedisTemplate jedisTemplate) {
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
        filterChainDefinitionMap.put("/**", "jwtAuthentication");
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);

        //自定义过滤器
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwtAuthentication", new JwtAuthenticationFilter(jedisTemplate));
        shiroFilter.setFilters(filterMap);

        return shiroFilter;
    }

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
