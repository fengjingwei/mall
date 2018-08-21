package com.hengxunda.springcloud.common.security.jwt;

import com.hengxunda.springcloud.common.persistence.DataEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountJWT extends DataEntity<AccountJWT> {

    private String authToken;

    private String account;

    private String password;

    private Integer sex;

    private String email;

    private String phone;

    private Integer status;

    public AccountJWT(Long id) {
        super();
        this.id = id;
    }
}
