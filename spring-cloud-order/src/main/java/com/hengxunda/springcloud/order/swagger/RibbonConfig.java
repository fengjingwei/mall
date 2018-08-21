package com.hengxunda.springcloud.order.swagger;

import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class RibbonConfig {

    @Bean
    public IPing ribbonPing() {
        return new PingUrl();
    }

    /**
     * 过滤掉那些因为一直连接失败的被标记为circuit tripped的后端server，并过滤掉那些高并发的的后端server（active connections 超过配置的阈值）
     *
     * @return
     */
    @Bean
    public IRule ribbonRule() {
        return new AvailabilityFilteringRule();
    }
}
