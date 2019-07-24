package com.hengxunda.springcloud.order.hystrix;

import com.hengxunda.springcloud.order.client.InventoryClient;
import com.hengxunda.springcloud.order.dto.InventoryDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class InventoryHystrix implements InventoryClient {

    @Override
    public String decrease(InventoryDTO inventoryDTO) {
        log.info("feign decrease error... {}", inventoryDTO);
        return "false";
    }

    @Override
    public Integer findByProductId(String productId) {
        log.info("feign findByProductId error... {}", productId);
        return null;
    }
}
