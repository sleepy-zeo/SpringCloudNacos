package com.sleepy.zeo.security;

import com.sleepy.zeo.security.access.ScnPermissionEvaluator;
import com.sleepy.zeo.security.exception.ScnAccessDeniedHandler;
import com.sleepy.zeo.security.exception.ScnAuthenticationEntryPoint;
import com.sleepy.zeo.security.jwt.JwtAuthenticationFilter;
import com.sleepy.zeo.security.jwt.JwtLoginFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ScnWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    private ScnPermissionEvaluator scnPermissionEvaluator;

    @Autowired
    @Qualifier("scn-user-service")
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setScnPermissionEvaluator(ScnPermissionEvaluator scnPermissionEvaluator) {
        this.scnPermissionEvaluator = scnPermissionEvaluator;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtLoginFilter jwtLoginFilter = new JwtLoginFilter(authenticationManager(), userDetailsService);
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(), userDetailsService);

        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/admin/**").access("hasRole('ADMIN')")
                .antMatchers("/user/**").access("hasRole('USER')")
                .anyRequest().authenticated()
                .and()
                .addFilter(jwtLoginFilter)
                .addFilter(jwtAuthenticationFilter);

        http.exceptionHandling()
                .authenticationEntryPoint(new ScnAuthenticationEntryPoint())
                .accessDeniedHandler(new ScnAccessDeniedHandler());

        DefaultWebSecurityExpressionHandler webSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        webSecurityExpressionHandler.setPermissionEvaluator(scnPermissionEvaluator);
        http.authorizeRequests()
                .expressionHandler(webSecurityExpressionHandler);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {

            // 这个方法好像没有用
            @Override
            public String encode(CharSequence charSequence) {
                log.info("encode: " + charSequence);
                return charSequence.toString();
            }

            // 只有这个方法有用到
            @Override
            public boolean matches(CharSequence charSequence, String encodedPassword) {
                log.info("matches: " + charSequence + ", encodedPassword: " + encodedPassword);
                return encodedPassword.equals(charSequence.toString());
            }
        });
    }
}
