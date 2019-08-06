package com.hengxunda.springcloud.service.common.redis;

import com.google.common.collect.Lists;
import com.hengxunda.springcloud.common.utils.FastJsonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Log4j2
@Component
public class RedisHelper {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public final String getStringFromRedis(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public final void putStringToRedis(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public final void putStringToRedis(String key, String value, long expireSeconds) {
        stringRedisTemplate.opsForValue().set(key, value, expireSeconds, TimeUnit.SECONDS);
    }

    public final void putObject(String key, Object value, long expireSeconds) {
        putObject(key, value, expireSeconds, TimeUnit.SECONDS);
    }

    public final void putObject(String key, Object value, long expireTime, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, expireTime, timeUnit);
    }

    public final Object getObject(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public final void removeObject(String key) {
        redisTemplate.delete(key);
    }

    public final boolean hSet(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        return true;
    }

    public final boolean hSet(String key, String hashKey, Object value, long expireSeconds) {
        return hSet(key, hashKey, value) && expire(key, expireSeconds);
    }

    public final Object hGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public final <T> T hGet(String key, Object hashKey, Class<T> clazz) {
        if (hHasKey(key, hashKey.toString())) {
            Object o = redisTemplate.opsForHash().get(key, hashKey.toString());
            if (o != null) {
                return FastJsonUtils.parseObject(o.toString(), clazz);
            }
        }
        return null;
    }

    public final <T> List<T> entries(String key, Class<T> clazz) {
        List<T> results = Lists.newArrayList();
        Map<String, String> entries = redisTemplate.opsForHash().entries(key);
        if (!entries.isEmpty()) {
            entries.values().forEach(json -> results.add(FastJsonUtils.parseObject(json, clazz)));
        }
        return results;
    }

    public final <T> void hmSet(String key, Map<String, T> value) {
        redisTemplate.opsForHash().putAll(key, value);
    }

    public final void hmSet(String key, Map<Object, Object> value, long expireSeconds) {
        redisTemplate.opsForHash().putAll(key, value);
        expire(key, expireSeconds);
    }

    public final Map<Object, Object> hmGet(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public final void hDel(String key, Object... hashKeys) {
        redisTemplate.opsForHash().delete(key, hashKeys);
    }

    public final double hIncr(String key, String hashKey, double delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    public final double hDecr(String key, String hashKey, double delta) {
        return hIncr(key, hashKey, -delta);
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

    public final void lSet(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }


    public final void lSet(String key, List<Object> value) {
        redisTemplate.opsForList().rightPushAll(key, value);
    }

    public final Object lSet(String key, Object value, long expireSeconds) {
        lSet(key, value);
        expire(key, expireSeconds);
        return true;
    }

    public final boolean lSet(String key, List<Object> value, long expireSeconds) {
        lSet(key, value);
        expire(key, expireSeconds);
        return true;
    }

    public final long lRemove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    public final boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public final boolean hHasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    public final void flushDb() {
        redisTemplate.execute((RedisCallback) connection -> {
            connection.flushDb();
            return null;
        });
    }
}