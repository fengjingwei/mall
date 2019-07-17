package com.hengxunda.springcloud.order.client;

import com.hengxunda.springcloud.order.config.FeignConfig;
import com.hengxunda.springcloud.order.dto.AccountDTO;
import com.hengxunda.springcloud.order.hystrix.AccountHystrix;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.math.BigDecimal;

@FeignClient(value = "account-service", configuration = FeignConfig.class, fallback = AccountHystrix.class)
public interface AccountClient {

    /**
     * 用户账户付款
     *
     * @param accountDO 实体类
     * @return true 成功
     */
    @RequestLine("POST /account-service/account/payment")
    Boolean payment(AccountDTO accountDO);

    /**
     * 获取用户账户信息
     *
     * @param userId 用户id
     * @return AccountDO
     */
    @RequestLine("GET /account-service/account/findByUserId?userId={userId}")
    BigDecimal findByUserId(@Param("userId") String userId);
}
