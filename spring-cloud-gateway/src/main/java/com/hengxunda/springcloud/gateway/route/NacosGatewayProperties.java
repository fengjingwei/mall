package com.hengxunda.springcloud.gateway.route;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.alibaba.nacos.NacosConfigProperties;
import org.springframework.context.annotation.Configuration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = NacosConfigProperties.PREFIX)
public class NacosGatewayProperties {

    private String serverAddr;

    private String dataId;

    private String group;

    private long timeout;
}
