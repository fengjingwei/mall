package com.hengxunda.springcloud.order.hystrix;

import com.hengxunda.springcloud.order.client.AccountClient;
import com.hengxunda.springcloud.order.dto.AccountDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Log4j2
@Component
public class AccountHystrix implements AccountClient {

    @Override
    public String payment(AccountDTO accountDO) {
        log.info("feign payment error... {}", accountDO);
        return "false";
    }

    @Override
    public BigDecimal findByUserId(String userId) {
        log.info("feign findByUserId error... {}", userId);
        return null;
    }
}
