package com.hengxunda.springcloud.gateway.filter;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.hengxunda.springcloud.common.constant.GatewayConstant;
import com.hengxunda.springcloud.common.persistence.AjaxResponse;
import com.hengxunda.springcloud.common.security.jwt.AccountJwt;
import com.hengxunda.springcloud.common.security.jwt.JwtUtils;
import com.hengxunda.springcloud.common.utils.FastJsonUtils;
import com.hengxunda.springcloud.common.utils.StringUtils;
import com.hengxunda.springcloud.service.common.redis.RedisHelper;
import io.netty.buffer.ByteBufAllocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class AuthenticationGatewayStrategyRouteFilter extends AbstractGatewayStrategyRouteFilter {

    @Autowired
    private RedisHelper redisHelper;

    /**
     * 初始化跳过网关鉴权路径配置,后期移到管理后台维护
     */
    @PostConstruct
    public void initSkipAuthenticationPath() {
        if (redisHelper.hasKey(GatewayConstant.GATEWAY_AUTH_SKIP_URLS)) {
            redisHelper.removeObject(GatewayConstant.GATEWAY_AUTH_SKIP_URLS);
        }
        putSkipAuthenticationPath();
    }

    private void putSkipAuthenticationPath() {
        final Set<String> urls = Sets.newHashSet();
        // urls.add("/account/payment");
        // urls.add("/account/findByUserId");

        // urls.add("/inventory/decrease");
        // urls.add("/inventory/findByProductId");

        urls.add("/order/listAll");
        // urls.add("/order/create");
        // urls.add("/order/orderPay");
        redisHelper.putString(GatewayConstant.GATEWAY_AUTH_SKIP_URLS, Joiner.on(",").skipNulls().join(urls));
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();
        final String upgrade = request.getHeaders().getUpgrade();
        if (StringUtils.equalsIgnoreCase("websocket", upgrade)) {
            return chain.filter(exchange);
        }
        final String path = request.getURI().getPath();
        if (StringUtils.endsWith(path, "defaultfallback")) {
            return chain.filter(exchange);
        }
        final String values = redisHelper.getString(GatewayConstant.GATEWAY_AUTH_SKIP_URLS);
        final List<String> paths = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(values);
        if (paths.stream().anyMatch(path::contains)) {
            /*if (request.getMethod() == HttpMethod.POST && !request.getHeaders().getContentType().includes(MediaType.MULTIPART_FORM_DATA)) {
                final Flux<DataBuffer> body = request.getBody();
                final AtomicReference<String> bodyRef = new AtomicReference<>();
                body.subscribe(dataBuffer -> {
                    final CharBuffer charBuffer = StandardCharsets.UTF_8.decode(dataBuffer.asByteBuffer());
                    DataBufferUtils.release(dataBuffer);
                    bodyRef.set(charBuffer.toString());
                });
                final String value = bodyRef.get();
                final DataBuffer bodyDataBuffer = stringBuffer(value);
                final Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);
                request = new ServerHttpRequestDecorator(request) {

                    @Override
                    public Flux<DataBuffer> getBody() {
                        return bodyFlux;
                    }
                };
            }
            return chain.filter(exchange.mutate().request(request).build());*/
            return chain.filter(exchange);
        }
        final HttpHeaders headers = request.getHeaders();
        final String jwt = headers.getFirst(GatewayConstant.AUTHORIZATION);
        final ServerHttpResponse exchangeResponse = exchange.getResponse();
        if (StringUtils.isBlank(jwt) || isBlackToken(jwt)) {
            return exchangeResponse.writeWith(Flux.just(exchangeResponse.bufferFactory().wrap(buildExchangeResponse(exchangeResponse, "Authorization Header There is no."))));
        }
        final AccountJwt accountJwt = JwtUtils.parseJwt(jwt, AccountJwt.class);
        if (Objects.isNull(accountJwt)) {
            return exchangeResponse.writeWith(Flux.just(exchangeResponse.bufferFactory().wrap(buildExchangeResponse(exchangeResponse, "Invalid request token."))));
        }
        return chain.filter(exchange);
    }

    private byte[] buildExchangeResponse(final ServerHttpResponse exchangeResponse, final String message) {
        exchangeResponse.setStatusCode(HttpStatus.OK);
        exchangeResponse.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        return FastJsonUtils.toJSONString(AjaxResponse.error(HttpStatus.UNAUTHORIZED.value(), message)).getBytes(StandardCharsets.UTF_8);
    }

    @Deprecated
    private DataBuffer stringBuffer(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

    private boolean isBlackToken(String jwt) {
        return redisHelper.hasKey(String.format(GatewayConstant.GATEWAY_JWT_BLACK_LIST, jwt));
    }

    @Override
    public int getOrder() {
        return -200;
    }
}
