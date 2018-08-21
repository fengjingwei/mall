package com.hengxunda.springcloud.gateway.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;

public interface BaseFallback extends FallbackProvider {

    String ORDER_SERVICE_ID = "order-service";

    String ACCOUNT_SERVICE_ID = "account-service";
}
