package com.hengxunda.springcloud.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class InventoryDTO implements Serializable {

    private static final long serialVersionUID = -8448573149565034376L;

    @ApiModelProperty(name = "productId", value = "商品id", example = "1", required = true)
    private String productId;

    @ApiModelProperty(name = "count", value = "购买数量", example = "10", required = true)
    private Integer count;
}
