package com.sleepy.oauth2.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class ScnAuthorizationServerConfigurerAdapter extends AuthorizationServerConfigurerAdapter {

    private PasswordEncoder passwordEncoder = new PasswordEncoder() {
        public String encode(CharSequence charSequence) {
            return charSequence.toString();
        }

        public boolean matches(CharSequence charSequence, String s) {
            return charSequence.toString().equals(s);
        }
    };

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                .withClient("scn_client_id")
                .secret(passwordEncoder.encode("scn_secret"))
                .authorizedGrantTypes("authorization_code")
                .scopes("read_userInfo", "read_userContacts")
                .redirectUris("http://localhost:8000/authCodeCallback");
    }
}
