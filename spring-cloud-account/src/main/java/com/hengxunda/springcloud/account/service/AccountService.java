package com.hengxunda.springcloud.account.service;

import com.hengxunda.springcloud.account.dto.AccountDTO;
import com.hengxunda.springcloud.account.entity.Account;
import com.hengxunda.springcloud.service.common.service.BaseService;
import org.dromara.hmily.annotation.Hmily;

public interface AccountService extends BaseService<Account> {

    /**
     * 扣款支付
     *
     * @param accountDTO
     * @return
     */
    @Hmily
    String payment(AccountDTO accountDTO);

    /**
     * 获取用户账户信息
     *
     * @param userId
     * @return {@link Account}
     */
    Account findByUserId(String userId);
}
