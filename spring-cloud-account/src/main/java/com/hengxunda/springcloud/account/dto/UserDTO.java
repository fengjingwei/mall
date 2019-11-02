package com.hengxunda.springcloud.account.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -3479973014221253748L;

    @ApiModelProperty(name = "account", value = "账户", example = "admin", required = true)
    private String account;

    @ApiModelProperty(name = "password", value = "密码", example = "123456", required = true)
    private String password;
}