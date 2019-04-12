package com.hengxunda.springcloud.service.common.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ServiceInstance {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    public String choose(String serviceId) {

        org.springframework.cloud.client.ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);

        log.info("{}:{}:{}", serviceInstance.getServiceId(), serviceInstance.getHost(), serviceInstance.getPort());

        return serviceInstance.getUri().toString() + "/" + serviceInstance.getServiceId();
    }
}
