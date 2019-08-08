package com.hengxunda.springcloud.account.controller;

import com.hengxunda.springcloud.account.dto.AccountDTO;
import com.hengxunda.springcloud.account.dto.UserDTO;
import com.hengxunda.springcloud.account.service.AccountService;
import com.hengxunda.springcloud.account.service.LoginService;
import com.hengxunda.springcloud.common.persistence.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private LoginService loginService;

    @PostMapping("payment")
    public String save(@RequestBody AccountDTO accountDO) {
        return accountService.payment(accountDO);
    }

    // @Authorization
    @GetMapping("findByUserId")
    public BigDecimal findByUserId(@RequestParam("userId") String userId) {
        return accountService.findByUserId(userId).getBalance();
    }

    @PostMapping("register")
    public AjaxResponse register(@RequestBody final UserDTO userDTO) {
        return AjaxResponse.success(userDTO);
    }

    @PostMapping("login")
    public AjaxResponse login(@RequestBody final UserDTO userDTO) {
        return AjaxResponse.success(loginService.login(userDTO.getAccount(), userDTO.getPassword()));
    }
}
