package com.hengxunda.springcloud.account.controller;

import com.hengxunda.springcloud.account.dto.AccountDTO;
import com.hengxunda.springcloud.account.dto.UserDTO;
import com.hengxunda.springcloud.account.entity.Account;
import com.hengxunda.springcloud.account.service.AccountService;
import com.hengxunda.springcloud.account.service.LoginService;
import com.hengxunda.springcloud.common.a.Assert;
import com.hengxunda.springcloud.common.persistence.AjaxResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "扣除账户余额")
    @ApiImplicitParam(name = "accountDTO", value = "请求对象", required = true, paramType = "body", dataType = "AccountDTO")
    @PostMapping("payment")
    public String payment(@RequestBody AccountDTO accountDTO) {
        return accountService.payment(accountDTO);
    }

    @ApiOperation(value = "获取账户余额")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "query", dataType = "String")
    @GetMapping("findByUserId")
    public BigDecimal findByUserId(@RequestParam("userId") String userId) {
        final Account account = accountService.findByUserId(userId);
        Assert.isNull(account, String.format("账户[%s]不存在", userId));
        return account.getBalance();
    }

    @ApiOperation(value = "注册")
    @ApiImplicitParam(name = "userDTO", value = "请求对象", required = true, paramType = "body", dataType = "UserDTO")
    @PostMapping("register")
    public AjaxResponse register(@RequestBody UserDTO userDTO) {
        return AjaxResponse.success(userDTO);
    }

    @ApiOperation(value = "登录")
    @ApiImplicitParam(name = "userDTO", value = "请求对象", required = true, paramType = "body", dataType = "UserDTO")
    @PostMapping("login")
    public AjaxResponse login(@RequestBody UserDTO userDTO) {
        return AjaxResponse.success(loginService.login(userDTO.getAccount(), userDTO.getPassword()));
    }
}
