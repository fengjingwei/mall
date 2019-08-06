package com.hengxunda.springcloud.common.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountJwt {

    private Long userId;

    private String jwt;

    private String account;

    public AccountJwt(Long userId) {
        setUserId(userId);
    }
}
