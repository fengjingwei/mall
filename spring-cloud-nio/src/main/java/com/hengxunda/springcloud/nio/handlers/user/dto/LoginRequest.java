package com.hengxunda.springcloud.nio.handlers.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest implements Serializable {

    private static final long serialVersionUID = -8528732451515766896L;

    /**
     * 登录类型
     */
    private Integer loginType;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 房间id
     */
    private String roomId;

    /**
     * 房间模式
     */
    private Integer mode;

    /**
     * 用户密钥
     */
    private String ak;

    private String nonce;
}
