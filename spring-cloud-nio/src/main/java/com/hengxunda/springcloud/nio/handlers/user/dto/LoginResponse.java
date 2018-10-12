package com.hengxunda.springcloud.nio.handlers.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse implements Serializable {

    /**
     * 用户id
     */
    private Long userId;
}
