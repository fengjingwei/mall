package com.hengxunda.springcloud.gateway.resolver;

import com.hengxunda.springcloud.common.constant.C;
import com.hengxunda.springcloud.service.common.redis.RedisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Mono;

@SpringBootConfiguration
@ComponentScan(basePackages = {"com.hengxunda.springcloud.service.common.redis"})
public class MyKeyResolver {

    private final RedisHelper redisHelper;

    @Autowired
    public MyKeyResolver(RedisHelper redisHelper) {
        this.redisHelper = redisHelper;
    }

    @Bean("userKeyResolver")
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst(redisHelper.getStringFromRedis(C.RATE_LIMITER_KEY)));
    }

    @Bean("ipKeyResolver")
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

    @Bean("apiKeyResolver")
    public KeyResolver apiKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getPath().value());
    }
}