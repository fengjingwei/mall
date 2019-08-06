package com.hengxunda.springcloud.gateway.filter;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;

public interface GatewayStrategyRouteFilter extends GlobalFilter, Ordered {
}
