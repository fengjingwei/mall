package com.hengxunda.springcloud.account.service.impl;

import com.hengxunda.springcloud.account.dto.AccountDTO;
import com.hengxunda.springcloud.account.entity.AccountDO;
import com.hengxunda.springcloud.account.mapper.AccountMapper;
import com.hengxunda.springcloud.account.service.AccountService;
import com.hengxunda.springcloud.common.exception.ServiceException;
import com.hengxunda.springcloud.common.utils.DateUtils;
import com.hengxunda.springcloud.service.common.service.AbstractCrudService;
import lombok.extern.log4j.Log4j2;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class AccountServiceImpl extends AbstractCrudService<AccountMapper, AccountDO> implements AccountService {

    @Override
    @Hmily(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    public String payment(AccountDTO accountDTO) {
        log.info("{}", "===========执行try付款方法==========");
        final AccountDO accountDO = dao.findByUserId(accountDTO.getUserId());
        accountDO.setBalance(accountDO.getBalance().subtract(accountDTO.getAmount()));
        accountDO.setFreezeAmount(accountDO.getFreezeAmount().add(accountDTO.getAmount()));
        accountDO.setUpdateTime(DateUtils.now());
        final int rows = dao.update(accountDO);
        if (rows != 1) {
            throw new ServiceException("资金不足");
        }
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
        if (rows != 1) {
            throw new ServiceException("取消扣减账户异常");
        }
        return "true";
    }

    @Override
    public AccountDO findByUserId(String userId) {
        return dao.findByUserId(userId);
    }
}
