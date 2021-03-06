package com.sleepy.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class ScnAuthorizationServerConfigurerAdapter extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    @Qualifier("scn-user-service")
    private UserDetailsService userDetailsService;

    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Bean
    public TokenStore jdbcTokenStore() {
        return new JdbcTokenStore(dataSource){
            @Override
            protected String extractTokenKey(String value) {
                return value;
            }
        };
    }

    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    // ??????????????????(Token Endpoint)???????????????
    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                // /oauth/token
                //
                // ??????????????????access token
                // curl -X POST
                // -d "client_id=scn_client_id&&client_secret=scn_client_secret&&grant_type=client_credentials"
                // http://localhost:1506/oauth/token
                // .allowFormAuthenticationForClients()
                // /oauth/token_key
                // ???????????????????????????
                .tokenKeyAccess("permitAll()")
                // /oauth/check_token
                // curl http://localhost:1506/oauth/check_token?token=e845b58c-39c2-4656-918e-a1b328768213
                .checkTokenAccess("permitAll()");
    }

    /**
     * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * clientId: ????????????????????????id
     * secret: ???????????????????????????
     * authorizedGrantTypes: ????????????????????????????????????????????????authorization_code
     * scope: ??????????????????
     * redirectUris: ?????????url
     *
     * DROP TABLE IF EXISTS `oauth_client_details`;
     * CREATE TABLE `oauth_client_details` (
     *   `client_id` varchar(48) NOT NULL,
     *   `client_secret` varchar(256) DEFAULT NULL,
     *   `resource_ids` varchar(256) DEFAULT NULL,
     *   `scope` varchar(256) DEFAULT NULL,
     *   `authorized_grant_types` varchar(256) DEFAULT NULL,
     *   `web_server_redirect_uri` varchar(256) DEFAULT NULL,
     *   `authorities` varchar(256) DEFAULT NULL,
     *   `access_token_validity` int(11) DEFAULT NULL,
     *   `refresh_token_validity` int(11) DEFAULT NULL,
     *   `additional_information` varchar(4096) DEFAULT NULL,
     *   `autoapprove` varchar(256) DEFAULT NULL,
     *   PRIMARY KEY (`client_id`)
     * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='???????????????';
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // ???????????????????????????????????????oauth_client_details
        clients.withClientDetails(clientDetails());
    }

    /**
     * ????????????(authorization)????????????(token)??????????????????????????????(token services)
     *
     * DROP TABLE IF EXISTS `oauth_access_token`;
     * CREATE TABLE `oauth_access_token` (
     *    `token_id` varchar(256) DEFAULT NULL,
     *    `token` blob,
     *    `authentication_id` varchar(128) NOT NULL,
     *    `user_name` varchar(256) DEFAULT NULL,
     *    `client_id` varchar(256) DEFAULT NULL,
     *    `authentication` blob,
     *    `refresh_token` varchar(256) DEFAULT NULL,
     *    PRIMARY KEY (`authentication_id`)
     *  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='????????????';
     *
     * DROP TABLE IF EXISTS `oauth_refresh_token`;
     * CREATE TABLE `oauth_refresh_token` (
     *    `token_id` varchar(256) NOT NULL,
     *    `token` blob,
     *    `authentication` blob,
     *    PRIMARY KEY (`token_id`)
     *  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='????????????';
     *
     * DROP TABLE IF EXISTS `oauth_code`;
     * CREATE TABLE `oauth_code` (
     *    `code` varchar(256) DEFAULT NULL,
     *    `authentication` blob,
     *    `create_ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP
     *  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='?????????';
     *
     * DROP TABLE IF EXISTS `oauth_approvals`;
     * CREATE TABLE `oauth_approvals` (
     *    `userId` varchar(256) DEFAULT NULL,
     *    `clientId` varchar(256) DEFAULT NULL,
     *    `scope` varchar(256) DEFAULT NULL,
     *    `status` varchar(10) DEFAULT NULL,
     *    `expiresAt` datetime DEFAULT NULL,
     *    `lastModifiedAt` datetime DEFAULT NULL
     *  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='??????????????????';
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                // ??????????????????????????????????????????oauth_approvals
                .approvalStore(approvalStore())
                // ????????????????????????????????????oauth_access_token
                .tokenStore(jdbcTokenStore())
                // ?????????????????????????????????oauth_code
                .authorizationCodeServices(authorizationCodeServices());
    }
}
