package com.hengxunda.springcloud.order.client;

import com.hengxunda.springcloud.order.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class AccountRibbon {

    @Autowired
    private RestTemplate restTemplate;

    public boolean payment(AccountDTO accountDO) {
        final String result = restTemplate.postForObject("http://account-service/account-service/account/payment", accountDO, String.class);
        return Boolean.getBoolean(result);
    }

    public BigDecimal findByUserId(String userId) {
        return restTemplate.getForObject("http://account-service/account-service/account/findByUserId?userId=" + userId, BigDecimal.class);
    }
}
