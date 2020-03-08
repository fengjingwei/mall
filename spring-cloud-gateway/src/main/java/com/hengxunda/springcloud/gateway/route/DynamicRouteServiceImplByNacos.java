package com.hengxunda.springcloud.gateway.route;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.hengxunda.springcloud.common.utils.FastJsonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.Executor;

@Log4j2
@Component
public class DynamicRouteServiceImplByNacos {

    @Autowired
    private DynamicRouteService dynamicRouteService;

    @Autowired
    private NacosGatewayProperties nacosGatewayProperties;

    @PostConstruct
    public void initialize() {
        // dynamicRouteByNacosListener("192.168.1.135:8848", "gateway-dynamic-route-dev.json", "DEFAULT_GROUP", 5000L);
        dynamicRouteByNacosListener(nacosGatewayProperties.getServerAddr(), nacosGatewayProperties.getDataId(), nacosGatewayProperties.getGroup(), nacosGatewayProperties.getTimeout());
    }

    /**
     * 监听Nacos Server下发的动态路由配置
     *
     * @param serverAddr
     * @param dataId
     * @param group
     * @param timeout
     */
    private void dynamicRouteByNacosListener(final String serverAddr, final String dataId, final String group, final long timeout) {
        try {
            final ConfigService configService = NacosFactory.createConfigService(serverAddr);
            final String config = configService.getConfig(dataId, group, timeout);
            log.info("Remote configuration -> {}", config);
            configService.addListener(dataId, group, new Listener() {

                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
                    final List<RouteDefinition> routeDefinitions = FastJsonUtils.toList(configInfo, RouteDefinition.class);
                    routeDefinitions.forEach(dynamicRouteService::updateRoute);
                }
            });
        } catch (NacosException e) {
            log.error("Dynamic route configuration exception -> {}", e.getErrMsg());
        }
    }
}
