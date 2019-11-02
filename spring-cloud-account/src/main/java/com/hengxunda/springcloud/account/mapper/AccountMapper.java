package com.hengxunda.springcloud.account.mapper;

import com.hengxunda.springcloud.account.dto.AccountDTO;
import com.hengxunda.springcloud.account.entity.Account;
import com.hengxunda.springcloud.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountMapper extends CrudDao<Account> {

    /**
     * 扣减账户余额
     *
     * @param account
     * @return
     */
    @Override
    @Update("update t_account set balance = #{balance}, " +
            "freeze_amount = #{freezeAmount}, update_time = #{updateTime} " +
            "where user_id = #{userId} and balance > 0")
    int update(Account account);

    /**
     * (确认)扣减账户余额
     *
     * @param accountDTO
     * @return
     */
    @Update("update t_account set " +
            "freeze_amount = freeze_amount - #{amount} " +
            "where user_id = #{userId} and freeze_amount > 0")
    int confirm(AccountDTO accountDTO);

    /**
     * (取消)扣减账户余额
     *
     * @param accountDTO
     * @return
     */
    @Update("update t_account set balance = balance + #{amount}, " +
            "freeze_amount = freeze_amount - #{amount} " +
            "where user_id = #{userId} and freeze_amount > 0")
    int cancel(AccountDTO accountDTO);

    /**
     * 根据userId获取用户账户信息
     *
     * @param userId
     * @return
     */
    @Select("select * from t_account where user_id = #{userId}")
    Account findByUserId(String userId);
}