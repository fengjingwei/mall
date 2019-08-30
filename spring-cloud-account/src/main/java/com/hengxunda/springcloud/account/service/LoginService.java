package com.hengxunda.springcloud.account.service;

import com.hengxunda.springcloud.common.security.jwt.AccountJwt;

public interface LoginService {

    /**
     * 登录
     *
     * @param account
     * @param password
     * @return {@link AccountJwt}
     */
    AccountJwt login(String account, String password);
}
