package com.hengxunda.springcloud.account.service.impl;

import com.hengxunda.springcloud.account.service.LoginService;
import com.hengxunda.springcloud.common.a.Assert;
import com.hengxunda.springcloud.common.security.jwt.AccountJWT;
import com.hengxunda.springcloud.common.security.jwt.JwtUtils;
import com.hengxunda.springcloud.common.utils.FastJsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Value("${p.account}")
    private String account;

    @Value("${p.password}")
    private String password;

    @Value("${jwt.timeout}")
    private long timeout;

    @Override
    public AccountJWT login(final String account, final String password) {

        log.info("account = [{}], password = [{}]", account, password);

        if (this.account.equals(account) && this.password.equals(password)) {
            AccountJWT accountJWT = AccountJWT.builder().account(account).password(password).build();
            String jwtToken = JwtUtils.createSimpleJWT(FastJsonUtils.toJSONString(accountJWT), timeout);
            accountJWT.setAuthToken(jwtToken);

            return accountJWT;
        }
        Assert.check(true, "用户名或密码错误");
        return null;
    }

}
