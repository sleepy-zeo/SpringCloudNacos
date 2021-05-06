package com.lullaby.ssjr.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Slf4j
@Configuration
public class JedisConfig {

    @Autowired
    private JedisProperties jedisProperties;

    @Bean
    public JedisPool redisPoolFactory() {
        try {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(jedisProperties.getPool().getMaxIdle());
            jedisPoolConfig.setMaxWaitMillis(jedisProperties.getPool().getMaxWait());
            jedisPoolConfig.setMaxTotal(jedisProperties.getPool().getMaxActive());
            jedisPoolConfig.setMinIdle(jedisProperties.getPool().getMinIdle());

            String password = jedisProperties.getPassword();
            if (password != null && "".equals(password.trim())) {
                password = null;
            }
            JedisPool jedisPool = new JedisPool(jedisPoolConfig, jedisProperties.getHost(), jedisProperties.getPort(), jedisProperties.getTimeout(), password);
            log.info("Connect to redis successfully, host:{}, port:{}", jedisProperties.getHost(), jedisProperties.getPort());
            return jedisPool;
        } catch (Exception e) {
            log.error("Connect to redis failed, msg:{}", e.getMessage());
        }
        return null;
    }
}
