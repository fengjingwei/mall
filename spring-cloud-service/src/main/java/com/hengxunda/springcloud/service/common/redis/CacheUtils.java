package com.hengxunda.springcloud.service.common.redis;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class CacheUtils {

    private static CacheManager cacheManager;

    private static RedisHelper redisHelper;

    @Bean
    public CacheManager setCache() {
        cacheManager = getCache();
        return cacheManager;
    }

    public static CacheManager getCache() {
        if (cacheManager == null) {
            synchronized (CacheUtils.class) {
                if (cacheManager == null) {

                }
            }
        }
        return cacheManager;
    }

    @Bean
    public RedisHelper setRedisHelper() {
        redisHelper = getRedisHelper();
        return redisHelper;
    }

    public static RedisHelper getRedisHelper() {
        if (redisHelper == null) {
            synchronized (CacheUtils.class) {
                if (redisHelper == null) {
                    redisHelper = new RedisHelper();
                }
            }
        }
        return redisHelper;
    }

}
