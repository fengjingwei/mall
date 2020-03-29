package com.hengxunda.springcloud.account.entity;

import com.hengxunda.springcloud.common.persistence.DataEntity;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Account extends DataEntity {

    private static final long serialVersionUID = -862497289608498627L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 可用余额
     */
    private BigDecimal balance;

    /**
     * 冻结余额
     */
    private BigDecimal freezeAmount;
}