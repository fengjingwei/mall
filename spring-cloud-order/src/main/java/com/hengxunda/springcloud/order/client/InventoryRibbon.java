package com.hengxunda.springcloud.order.client;

import com.hengxunda.springcloud.common.utils.RestTemplateUtils;
import com.hengxunda.springcloud.order.dto.InventoryDTO;
import com.hengxunda.springcloud.service.common.service.ServiceInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryRibbon {

    @Autowired
    private ServiceInstance serviceInstance;

    public Boolean decrease(InventoryDTO inventoryDTO) {
        return RestTemplateUtils.getRestTemplate().postForObject(serviceInstance.choose("inventory-service") + "/inventory/decrease", inventoryDTO, Boolean.class);
    }

    public Integer findByProductId(String productId) {
        return RestTemplateUtils.getRestTemplate().getForObject(serviceInstance.choose("inventory-service") + "/inventory/findByProductId?productId=" + productId, Integer.class);
    }
}

