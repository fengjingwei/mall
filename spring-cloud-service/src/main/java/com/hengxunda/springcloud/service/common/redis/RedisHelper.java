package com.hengxunda.springcloud.service.common.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisHelper implements CacheManager {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public final String getStringFromRedis(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public final void putStringToRedis(String key, String value, long expireSeconds) {
        stringRedisTemplate.opsForValue().set(key, value, expireSeconds, TimeUnit.SECONDS);
    }

    public final void putObject(String key, Object value, long expireMinutes) {
        redisTemplate.opsForValue().set(key, value, expireMinutes, TimeUnit.MINUTES);
    }

    public final Object getObject(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public final void removeObject(String key) {
        redisTemplate.delete(key);
    }

    public final boolean hset(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        return true;
    }

    public final boolean hset(String key, String hashKey, Object value, long expireSeconds) {
        return hset(key, hashKey, value) && expire(key, expireSeconds);
    }

    public final Object hget(String key, String value) {
        return redisTemplate.opsForHash().get(key, value);
    }

    public final boolean hmset(String key, Map<Object, Object> value) {
        redisTemplate.opsForHash().putAll(key, value);
        return true;
    }

    public final void hmset(String key, Map<Object, Object> value, long expireSeconds) {
        redisTemplate.opsForHash().putAll(key, value);
        expire(key, expireSeconds);
    }

    public final Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public final void hdel(String key, Object... values) {
        redisTemplate.opsForHash().delete(key, values);
    }

    public final boolean hHasKey(String key, String value) {
        return redisTemplate.opsForHash().hasKey(key, value);
    }

    public final double hincr(String key, String value, double by) {
        return redisTemplate.opsForHash().increment(key, value, by);
    }

    public final double hdecr(String key, String value, double by) {
        return redisTemplate.opsForHash().increment(key, value, -by);
    }

    public final boolean expire(String key, long expireSeconds) {
        if (expireSeconds > 0) {
            return redisTemplate.expire(key, expireSeconds, TimeUnit.SECONDS);
        }
        return false;
    }

    public final List<Object> lGet(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    public final long lGetListSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    public final Object lGetIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    public final Object lSet(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    public final Object lSet(String key, Object value, long expireSeconds) {
        lSet(key, value);
        expire(key, expireSeconds);
        return true;
    }

    public final long lSet(String key, List<Object> value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    public final boolean lSet(String key, List<Object> value, long expireSeconds) {
        lSet(key, value);
        expire(key, expireSeconds);
        return true;
    }

    public final long lRemove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
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