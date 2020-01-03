package com.hengxunda.springcloud.order.client;

import com.hengxunda.springcloud.order.dto.InventoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class InventoryRibbon {

    @Autowired
    private RestTemplate restTemplate;

    public boolean decrease(InventoryDTO inventoryDTO) {
        final String result = restTemplate.postForObject("http://inventory-service/inventory-service/inventory/decrease", inventoryDTO, String.class);
        return Boolean.getBoolean(result);
    }

    public Integer findByProductId(String productId) {
        return restTemplate.getForObject("http://inventory-service/inventory-service/inventory/findByProductId?productId=" + productId, Integer.class);
    }
}

