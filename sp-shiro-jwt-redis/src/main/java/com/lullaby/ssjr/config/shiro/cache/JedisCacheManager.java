package com.lullaby.ssjr.config.shiro.cache;

import com.lullaby.ssjr.config.redis.JedisTemplate;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

public class JedisCacheManager implements CacheManager {

    private final JedisTemplate jedisTemplate;

    public JedisCacheManager(JedisTemplate jedisTemplate) {
        this.jedisTemplate = jedisTemplate;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new JedisCache<K, V>(jedisTemplate);
    }
}
