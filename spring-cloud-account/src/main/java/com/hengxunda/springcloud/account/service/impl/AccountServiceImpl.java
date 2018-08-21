package com.hengxunda.springcloud.account.service.impl;

import com.hengxunda.springcloud.account.dto.AccountDTO;
import com.hengxunda.springcloud.account.entity.AccountDO;
import com.hengxunda.springcloud.account.mapper.AccountMapper;
import com.hengxunda.springcloud.account.service.AccountService;
import com.hengxunda.springcloud.common.exception.ServiceException;
import com.hengxunda.springcloud.common.utils.DateUtils;
import com.hengxunda.springcloud.service.common.service.AbstractCrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl extends AbstractCrudService<AccountMapper, AccountDO> implements AccountService {

    @Override
    @Transactional
    public boolean payment(AccountDTO accountDTO) {

        final AccountDO accountDO = dao.findByUserId(accountDTO.getUserId());
        accountDO.setBalance(accountDO.getBalance().subtract(accountDTO.getAmount()));
        accountDO.setFreezeAmount(accountDO.getFreezeAmount().add(accountDTO.getAmount()));
        accountDO.setUpdateTime(DateUtils.getLocalDateTime());
        final int update = dao.update(accountDO);
        if (update != 1) {
            throw new ServiceException("资金不足!");
        }
        return true;
    }

    @Override
    public AccountDO findByUserId(String userId) {
        return dao.findByUserId(userId);
    }
}
