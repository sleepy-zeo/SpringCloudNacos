package com.sleepy.oath2.security.oath2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@Configuration
@EnableAuthorizationServer
public class ScnAuthorizationServerConfigurerAdapter extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder = new PasswordEncoder() {
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
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                //client_id(请求授权码的客户端/应用id)
                .withClient("client_id")
                //client_secret(请求授权码的客户端/应用密钥)
                .secret(getPasswordEncoder().encode("client_secret"))
                //授权模式（分为：简单模式，授权码模式，密码模式，客户端模式）
                .authorizedGrantTypes("authorization_code")
                //客户端/应用授权范围
                .scopes("app")
                //授权码模式下通过回调地址返回授权码给gateway或者app应用
                .redirectUris("http://localhost:1703");
    }
}
