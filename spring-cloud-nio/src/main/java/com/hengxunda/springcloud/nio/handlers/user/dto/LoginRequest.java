package com.hengxunda.springcloud.nio.handlers.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest implements Serializable {

    private Integer loginType;// 登录类型

    private Long userId;// 用户id

    private String roomId;// 房间id

    private Integer mode;// 房间模式

    private String ak;// 用户密钥

    private String nonce;

}
