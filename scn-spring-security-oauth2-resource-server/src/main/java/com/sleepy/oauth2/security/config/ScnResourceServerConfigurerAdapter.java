package com.sleepy.oauth2.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
@EnableResourceServer
public class ScnResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {

    @Primary
    @Bean
    public RemoteTokenServices getTokenService() {
        RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl("http://localhost:1506/oauth/check_token");
        tokenService.setClientId("scn_client_id");
        tokenService.setClientSecret("scn_secret");
        return tokenService;
    }
}
