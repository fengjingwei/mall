package com.hengxunda.springcloud.account.service;

import com.hengxunda.springcloud.common.security.jwt.AccountJWT;

public interface LoginService {

    AccountJWT login(String account, String password);
}
