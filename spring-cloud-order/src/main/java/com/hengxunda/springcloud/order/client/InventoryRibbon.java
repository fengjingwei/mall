package com.hengxunda.springcloud.order.client;

import com.hengxunda.springcloud.order.dto.InventoryDTO;
import com.hengxunda.springcloud.service.common.service.ServiceInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class InventoryRibbon {

    @Autowired
    private ServiceInstance serviceInstance;

    @Autowired
    private RestTemplate restTemplate;

    public Boolean decrease(InventoryDTO inventoryDTO) {
        return restTemplate.postForObject("http://inventory-service/inventory-service/inventory/decrease", inventoryDTO, Boolean.class);
    }

    public Integer findByProductId(String productId) {
        return restTemplate.getForObject("http://inventory-service/inventory-service/inventory/findByProductId?productId=" + productId, Integer.class);
    }
}

