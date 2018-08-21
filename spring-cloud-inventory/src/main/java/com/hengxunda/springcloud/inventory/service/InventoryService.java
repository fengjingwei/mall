package com.hengxunda.springcloud.inventory.service;

import com.hengxunda.springcloud.inventory.dto.InventoryDTO;
import com.hengxunda.springcloud.inventory.entity.InventoryDO;
import com.hengxunda.springcloud.service.common.service.BaseService;

public interface InventoryService extends BaseService<InventoryDO> {

    /**
     * 扣减库存操作
     *
     * @param inventoryDTO 库存DTO对象
     * @return true
     */
    Boolean decrease(InventoryDTO inventoryDTO);

    /**
     * 获取商品库存信息
     *
     * @param productId 商品id
     * @return InventoryDO
     */
    InventoryDO findByProductId(String productId);
}
