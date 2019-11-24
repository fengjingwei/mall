package com.hengxunda.springcloud.gateway.fallback;

import com.hengxunda.springcloud.common.persistence.AjaxResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayFallback {

    @GetMapping("defaultfallback")
    public AjaxResponse defaultfallback() {
        return AjaxResponse.error("网络异常,请稍后");
    }
}
