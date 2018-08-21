package com.hengxunda.springcloud.service.common.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisHelper implements CacheManager {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final long EXPIRE_TIME_IN_MINUTES = 60 * 60 * 24; // redis过期时间,单位秒

    public final String getStringFromRedis(String key) {
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        return opsForValue.get(key);
    }

    public final void putStringToRedis(String key, String value, int expireSeconds) {
        ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
        opsForValue.set(key, value, expireSeconds, TimeUnit.SECONDS);
    }

    public final void putObject(Object key, Object value) {
        ValueOperations opsForValue = redisTemplate.opsForValue();
        opsForValue.set(key, value, EXPIRE_TIME_IN_MINUTES, TimeUnit.MINUTES);
    }

    public final void putObject(Object key, Object value, long expireTime) {
        ValueOperations opsForValue = redisTemplate.opsForValue();
        opsForValue.set(key, value, expireTime, TimeUnit.MINUTES);
    }

    public final Object getObject(Object key) {
        ValueOperations opsForValue = redisTemplate.opsForValue();
        return opsForValue.get(key);
    }

    public final void removeObject(Object key) {
        redisTemplate.delete(key);
    }

    public final void clear() {
        redisTemplate.execute((RedisCallback) connection -> {
            connection.flushDb();
            return null;
        });
    }

    @Override
    public String getSet(String key, String value) {
        Object object = null;
        try {
            object = redisTemplate.execute((RedisCallback<Object>) connection -> {
                StringRedisSerializer serializer = new StringRedisSerializer();
                byte[] ret = connection.getSet(serializer.serialize(key), serializer.serialize(value));
                connection.close();
                return serializer.deserialize(ret);
            });
        } catch (Exception e) {
            log.error("getSet redis error, key : {}", key);
        }
        return object != null ? (String) object : null;
    }

    @Override
    public boolean setnx(String key, String value) {
        Object object = null;
        try {
            object = redisTemplate.execute((RedisCallback<Object>) connection -> {
                StringRedisSerializer serializer = new StringRedisSerializer();
                Boolean success = connection.setNX(serializer.serialize(key), serializer.serialize(value));
                connection.close();
                return success;
            });
        } catch (Exception e) {
            log.error("setnx redis error, key : {}", key);
        }
        return object != null ? (Boolean) object : false;
    }

    @Override
    public Object get(String key) {
        Object object = null;
        try {
            object = redisTemplate.execute((RedisCallback<Object>) connection -> {
                StringRedisSerializer serializer = new StringRedisSerializer();
                byte[] data = connection.get(serializer.serialize(key));
                connection.close();
                if (data == null) {
                    return null;
                }
                return serializer.deserialize(data);
            });
        } catch (Exception e) {
            log.error("get redis error, key : {}", key);
        }
        return object;
    }

}
