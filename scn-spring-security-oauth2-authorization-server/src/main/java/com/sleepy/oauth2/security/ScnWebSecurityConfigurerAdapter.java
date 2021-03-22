package com.sleepy.oauth2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ScnWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //配置内存模式默认账户用以认证
        auth.inMemoryAuthentication()
                //账户名
                .withUser("admin")
                //密码（必须encode加密）
                .password(passwordEncoder.encode("123456"))
                //角色
                .roles("ADMIN")
                .and()
                .withUser("guest")
                .password(passwordEncoder.encode("123456"))
                .roles("GUEST");
    }

}
