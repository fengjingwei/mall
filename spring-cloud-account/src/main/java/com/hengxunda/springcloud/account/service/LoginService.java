package com.hengxunda.springcloud.account.service;

import com.hengxunda.springcloud.common.security.jwt.AccountJWT;

public interface LoginService {

    /**
     * 登录
     *
     * @param account
     * @param password
     * @return
     */
    AccountJWT login(String account, String password);
}
