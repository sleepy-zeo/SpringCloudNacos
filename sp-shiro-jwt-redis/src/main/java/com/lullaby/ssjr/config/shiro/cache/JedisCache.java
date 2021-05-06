package com.lullaby.ssjr.config.shiro.cache;

import com.lullaby.ssjr.common.Constants;
import com.lullaby.ssjr.config.redis.JedisTemplate;
import com.lullaby.ssjr.utils.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Slf4j
public class JedisCache<K, V> implements Cache<K, V> {

    private final JedisTemplate jedisTemplate;

    public JedisCache(JedisTemplate jedisTemplate) {
        this.jedisTemplate = jedisTemplate;
    }

    private String jedisKey(Object key) {
        return jedisKeyPrefix() + key.toString();
    }

    private String jedisKeyPrefix() {
        return Constants.PREFIX_SHIRO_CACHE;
    }

    @Override
    public V get(K k) throws CacheException {
        String key = jedisKey(k);
        if (jedisTemplate.exists(key)) {
            return (V) jedisTemplate.getObject(key);
        }
        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        String key = jedisKey(k);
        try {
            PropertiesUtil.readProperties("config.properties");
        } catch (IOException e) {
            log.error(e.getMessage());
            jedisTemplate.setObject(key, v);
            return v;
        }
        String shiroCacheExpireTime = PropertiesUtil.getProperty("shiroCacheExpireTime");
        jedisTemplate.setObject(key, v, Integer.parseInt(shiroCacheExpireTime));
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        String key = jedisKey(k);
        if (jedisTemplate.exists(key)) {
            jedisTemplate.delKey(key);
        }
        return null;
    }

    @Override
    public void clear() throws CacheException {
        jedisTemplate.flushDB();
    }

    @Override
    public int size() {
        return jedisTemplate.dbSize((jedisKeyPrefix() + "*").getBytes());
    }

    @Override
    public Set<K> keys() {
        return (Set<K>) jedisTemplate.keys((jedisKeyPrefix() + "*").getBytes());
    }

    @Override
    public Collection<V> values() {
        Set keys = keys();
        List<Object> values = new ArrayList<Object>();
        for (Object key : keys) {
            values.add(jedisTemplate.getObject((String) key));
        }
        return (Collection<V>) values;
    }
}
