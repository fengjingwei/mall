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

    private String jwt;

    private Long userId;

    private String account;

    private String loginTime;

    // ...

    public AccountJwt(Long userId) {
        setUserId(userId);
    }
}
