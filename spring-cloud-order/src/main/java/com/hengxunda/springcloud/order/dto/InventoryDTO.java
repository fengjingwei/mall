package com.hengxunda.springcloud.order.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class InventoryDTO implements Serializable {

    private static final long serialVersionUID = 8229355519336565493L;

    /**
     * 商品id
     */
    private String productId;

    /**
     * 数量
     */
    private Integer count;
}
