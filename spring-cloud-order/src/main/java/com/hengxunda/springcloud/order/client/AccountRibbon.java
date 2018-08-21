package com.hengxunda.springcloud.order.client;

import com.hengxunda.springcloud.common.utils.RestTemplateUtils;
import com.hengxunda.springcloud.order.dto.AccountDTO;
import com.hengxunda.springcloud.service.common.service.ServiceInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountRibbon {

    @Autowired
    private ServiceInstance serviceInstance;

    public Boolean payment(AccountDTO accountDO) {
        return RestTemplateUtils.getRestTemplate().postForObject(serviceInstance.choose("account-service") + "/account/payment", accountDO, Boolean.class);
    }

    public BigDecimal findByUserId(String userId) {
        return RestTemplateUtils.getRestTemplate().getForObject(serviceInstance.choose("account-service") + "/account/findByUserId?userId=" + userId, BigDecimal.class);
    }

}
