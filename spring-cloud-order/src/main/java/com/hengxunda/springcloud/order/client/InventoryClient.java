package com.hengxunda.springcloud.order.client;

import com.hengxunda.springcloud.order.dto.InventoryDTO;
import com.hengxunda.springcloud.order.hystrix.InventoryHystrix;
import com.hengxunda.springcloud.order.swagger.FeignConfig;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "inventory-service", configuration = FeignConfig.class, fallback = InventoryHystrix.class)
public interface InventoryClient {

    /**
     * 库存扣减
     *
     * @param inventoryDTO 实体对象
     * @return true 成功
     */
    @RequestLine("POST /inventory-service/inventory/decrease")
    Boolean decrease(InventoryDTO inventoryDTO);

    /**
     * 获取商品库存
     *
     * @param productId 商品id
     * @return InventoryDO
     */
    @RequestLine("GET /inventory-service/inventory/findByProductId?productId={productId}")
    Integer findByProductId(@Param("productId") String productId);
}
