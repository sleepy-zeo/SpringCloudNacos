package com.lullaby.ssjr.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Slf4j
@Configuration
public class JedisConfig {

    private static JedisPool pool;
    private static JedisProperties jedisProperties;

    @Autowired
    public void setJedisProperties(JedisProperties jedisProperties) {
        JedisConfig.jedisProperties = jedisProperties;
    }

    public static JedisPool getPool() {
        if (pool == null) {

            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(jedisProperties.getPool().getMaxIdle());
            jedisPoolConfig.setMaxWaitMillis(jedisProperties.getPool().getMaxWait());
            jedisPoolConfig.setMaxTotal(jedisProperties.getPool().getMaxActive());
            jedisPoolConfig.setMinIdle(jedisProperties.getPool().getMinIdle());
            jedisPoolConfig.setTestOnBorrow(true);
            jedisPoolConfig.setTestOnReturn(true);
            jedisPoolConfig.setBlockWhenExhausted(true);

            String password = jedisProperties.getPassword();
            if (password != null && "".equals(password.trim())) {
                password = null;
            }
            pool = new JedisPool(jedisPoolConfig, jedisProperties.getHost(), jedisProperties.getPort(), jedisProperties.getTimeout(), password);
        }
        return pool;
    }

}
