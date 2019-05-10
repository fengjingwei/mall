package com.hengxunda.springcloud.account.controller;

import com.hengxunda.springcloud.account.dto.AccountDTO;
import com.hengxunda.springcloud.account.dto.UserDTO;
import com.hengxunda.springcloud.account.service.AccountService;
import com.hengxunda.springcloud.account.service.LoginService;
import com.hengxunda.springcloud.common.persistence.AjaxResponse;
import com.hengxunda.springcloud.common.security.jwt.AccountJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private LoginService loginService;

    @RequestMapping("payment")
    public Boolean save(@RequestBody AccountDTO accountDO) {
        return accountService.payment(accountDO);
    }

    //@Authorization
    @RequestMapping(value = "findByUserId")
    public BigDecimal findByUserId(@RequestParam("userId") String userId) {
        log.info("userId = {}", userId);
        return accountService.findByUserId(userId).getBalance();
    }

    @PostMapping("login")
    public AjaxResponse login(@RequestBody final UserDTO userDTO) {
        final AccountJWT accountJWT = loginService.login(userDTO.getAccount(), userDTO.getPassword());
        return AjaxResponse.success(accountJWT);
    }
}
