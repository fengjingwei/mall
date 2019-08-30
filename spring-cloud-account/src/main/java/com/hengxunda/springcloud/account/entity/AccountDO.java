package com.hengxunda.springcloud.account.entity;

import com.hengxunda.springcloud.common.persistence.DataEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDO extends DataEntity<AccountDO> {

    private static final long serialVersionUID = -862497289608498627L;

    private String userId;

    private BigDecimal balance;

    private BigDecimal freezeAmount;
}