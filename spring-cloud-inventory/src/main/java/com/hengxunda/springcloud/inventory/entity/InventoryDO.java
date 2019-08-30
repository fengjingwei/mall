package com.hengxunda.springcloud.inventory.entity;

import com.hengxunda.springcloud.common.persistence.DataEntity;
import lombok.Data;

@Data
public class InventoryDO extends DataEntity<InventoryDO> {

    private static final long serialVersionUID = -7684737526853183642L;

    /**
     * 商品id
     */
    private String productId;

    /**
     * 总库存
     */
    private Integer totalInventory;

    /**
     * 锁定库存
     */
    private Integer lockInventory;
}
