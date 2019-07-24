package com.hengxunda.springcloud.service.common.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;

@Log4j2
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
