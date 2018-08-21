package com.hengxunda.springcloud.account.service;

import com.hengxunda.springcloud.account.dto.AccountDTO;
import com.hengxunda.springcloud.account.entity.AccountDO;
import com.hengxunda.springcloud.service.common.service.BaseService;

public interface AccountService extends BaseService<AccountDO> {

    /**
     * 扣款支付
     *
     * @param accountDTO
     * @return
     */
    boolean payment(AccountDTO accountDTO);

    /**
     * 获取用户账户信息
     *
     * @param userId
     * @return
     */
    AccountDO findByUserId(String userId);
}
