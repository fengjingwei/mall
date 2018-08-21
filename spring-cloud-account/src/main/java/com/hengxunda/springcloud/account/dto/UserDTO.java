package com.hengxunda.springcloud.account.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -3479973014221253748L;

    private String account;

    private String password;

}
