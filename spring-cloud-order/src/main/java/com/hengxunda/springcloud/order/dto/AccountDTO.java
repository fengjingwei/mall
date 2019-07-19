package com.hengxunda.springcloud.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class AccountDTO implements Serializable {

    private static final long serialVersionUID = 7223470850578998427L;

    @ApiModelProperty(name = "userId", notes = "用户id", example = "10000", required = true)
    private String userId;

    @ApiModelProperty(name = "amount", notes = "扣款金额", example = "100", required = true)
    private BigDecimal amount;
}
