package com.sleepy.zeo.security;

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
import org.springframework.util.DigestUtils;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class ScnWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("scn-user-service")
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("configure");
        JwtLoginFilter jwtLoginFilter = new JwtLoginFilter(authenticationManager(), userDetailsService);
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(), userDetailsService);

        http.csrf().disable();

        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .addFilter(jwtLoginFilter)
                .addFilterBefore(jwtAuthenticationFilter, JwtLoginFilter.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                log.info("encode: " + charSequence);
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                log.info("matches: " + charSequence + "--" + s);
                return s.equals(charSequence.toString());
            }
        });
    }
}
