package com.hengxunda.springcloud.inventory.service;

import com.hengxunda.springcloud.inventory.dto.InventoryDTO;
import com.hengxunda.springcloud.inventory.entity.Inventory;
import com.hengxunda.springcloud.service.common.service.BaseService;
import org.dromara.hmily.annotation.Hmily;

public interface InventoryService extends BaseService<Inventory> {

    /**
     * 扣减库存操作
     *
     * @param inventoryDTO
     * @return
     */
    @Hmily
    String decrease(InventoryDTO inventoryDTO);

    /**
     * 获取商品库存信息
     *
     * @param productId
     * @return {@link Inventory}
     */
    Inventory findByProductId(String productId);
}
