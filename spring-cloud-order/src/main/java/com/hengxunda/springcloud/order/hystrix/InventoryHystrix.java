package com.hengxunda.springcloud.order.hystrix;

import com.hengxunda.springcloud.order.client.InventoryClient;
import com.hengxunda.springcloud.order.dto.InventoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InventoryHystrix implements InventoryClient {

    @Override
    public Boolean decrease(InventoryDTO inventoryDTO) {

        log.info("feign decrease error... {}", inventoryDTO);

        return false;
    }

    @Override
    public Integer findByProductId(String productId) {

        log.info("feign findByProductId error... {}", productId);

        return null;
    }
}
