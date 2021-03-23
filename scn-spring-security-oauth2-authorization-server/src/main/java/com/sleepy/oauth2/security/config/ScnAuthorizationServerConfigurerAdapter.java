package com.sleepy.oauth2.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class ScnAuthorizationServerConfigurerAdapter extends AuthorizationServerConfigurerAdapter {

    private DataSource dataSource;
    private RedisConnectionFactory redisConnectionFactory;

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

    @Autowired
    public void setRedisConnectionFactory(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Bean
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    // 配置令牌端点(Token Endpoint)的安全约束
    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * 客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息
     * clientId: 必须，标识客户的id
     * secret: 可选，客户端安全码
     * authorizedGrantTypes: 客户端使用的授权类型，比如可以是authorization_code
     * scope: 客户受限范围
     * redirectUris: 重定向url
     *
     * DROP TABLE IF EXISTS `oauth_client_details`;
     * CREATE TABLE `oauth_client_details` (
     *   `client_id` varchar(48) NOT NULL,
     *   `resource_ids` varchar(256) DEFAULT NULL,
     *   `client_secret` varchar(256) DEFAULT NULL,
     *   `scope` varchar(256) DEFAULT NULL,
     *   `authorized_grant_types` varchar(256) DEFAULT NULL,
     *   `web_server_redirect_uri` varchar(256) DEFAULT NULL,
     *   `authorities` varchar(256) DEFAULT NULL,
     *   `access_token_validity` int(11) DEFAULT NULL,
     *   `refresh_token_validity` int(11) DEFAULT NULL,
     *   `additional_information` varchar(4096) DEFAULT NULL,
     *   `autoapprove` varchar(256) DEFAULT NULL,
     *   PRIMARY KEY (`client_id`)
     * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // jdbc存储获取注释中的数据
        clients.withClientDetails(clientDetails());

    }

    // 配置授权(authorization)以及令牌(token)的访问端点和令牌服务(token services)
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        super.configure(endpoints);
        endpoints.tokenStore(redisTokenStore());
    }
}
