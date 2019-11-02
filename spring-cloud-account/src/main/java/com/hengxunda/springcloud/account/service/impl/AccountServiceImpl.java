package com.hengxunda.springcloud.account.service.impl;

import com.hengxunda.springcloud.account.dto.AccountDTO;
import com.hengxunda.springcloud.account.entity.Account;
import com.hengxunda.springcloud.account.mapper.AccountMapper;
import com.hengxunda.springcloud.account.service.AccountService;
import com.hengxunda.springcloud.common.a.Assert;
import com.hengxunda.springcloud.common.utils.DateUtils;
import com.hengxunda.springcloud.service.common.service.AbstractCrudService;
import lombok.extern.log4j.Log4j2;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class AccountServiceImpl extends AbstractCrudService<AccountMapper, Account> implements AccountService {

    @Override
    @Hmily(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    public String payment(AccountDTO accountDTO) {
        log.info("{}", "===========执行try付款方法==========");
        final Account account = dao.findByUserId(accountDTO.getUserId());
        account.setBalance(account.getBalance().subtract(accountDTO.getAmount()));
        account.setFreezeAmount(account.getFreezeAmount().add(accountDTO.getAmount()));
        account.setUpdateTime(DateUtils.now());
        final int rows = dao.update(account);
        Assert.state(rows != 1, "余额不足");
        return "true";
    }

    public String confirmMethod(AccountDTO accountDTO) {
        log.info("{}", "===========执行confirm付款方法==========");
        dao.confirm(accountDTO);
        return "true";
    }

    public String cancelMethod(AccountDTO accountDTO) {
        log.info("{}", "===========执行cancel付款方法==========");
        final int rows = dao.cancel(accountDTO);
        Assert.state(rows != 1, "取消扣减账户异常");
        return "true";
    }

    @Override
    public Account findByUserId(String userId) {
        return dao.findByUserId(userId);
    }
}
