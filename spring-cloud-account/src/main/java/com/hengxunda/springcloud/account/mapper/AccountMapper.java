package com.hengxunda.springcloud.account.mapper;

import com.hengxunda.springcloud.account.entity.AccountDO;
import com.hengxunda.springcloud.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountMapper extends CrudDao<AccountDO> {

    /**
     * 扣减账户余额
     *
     * @param accountDO 实体类
     * @return rows
     */
    @Update("update account set balance = #{balance}," +
            " freeze_amount = #{freezeAmount}, update_time = #{updateTime}" +
            " where user_id = #{userId} and balance > 0")
    int update(AccountDO accountDO);

    /**
     * 根据userId获取用户账户信息
     *
     * @param userId 用户id
     * @return AccountDO
     */
    @Select("select * from account where user_id = #{userId}")
    AccountDO findByUserId(String userId);
}
