package com.hengxunda.springcloud.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderDTO implements Serializable {

    private static final long serialVersionUID = 7223470850578998427L;

    @ApiModelProperty(name = "userId", notes = "用户id", example = "10000", required = true)
    private String userId;

    @ApiModelProperty(name = "productId", notes = "商品id", example = "1", required = true)
    private String productId;

    @ApiModelProperty(name = "count", notes = "购买数量", example = "10", required = true)
    private Integer count;

    @ApiModelProperty(name = "totalAmount", notes = "扣款金额", example = "100", required = true)
    private BigDecimal totalAmount;
}
