package com.hengxunda.springcloud.gateway.resolver;

import com.hengxunda.springcloud.common.constant.GatewayConstant;
import com.hengxunda.springcloud.common.utils.StringUtils;
import com.hengxunda.springcloud.service.common.redis.RedisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

@Configuration
@ComponentScan(basePackages = {"com.hengxunda.springcloud.service.common.redis"})
public class MyKeyResolver {

    private final RedisHelper redisHelper;

    @Autowired
    public MyKeyResolver(RedisHelper redisHelper) {
        this.redisHelper = redisHelper;
    }

    @Bean("userKeyResolver")
    public KeyResolver userKeyResolver() {
        String filterKey = redisHelper.getString(GatewayConstant.GATEWAY_RATE_LIMITER);
        if (StringUtils.isBlank(filterKey)) {
            filterKey = "userId";
            redisHelper.putString(GatewayConstant.GATEWAY_RATE_LIMITER, filterKey);
        }
        final String finalFilterKey = filterKey;
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst(finalFilterKey));
    }

    @Primary
    @Bean("ipKeyResolver")
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

    @Bean("apiKeyResolver")
    public KeyResolver apiKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getPath().value());
    }
}