package com.hengxunda.springcloud.gateway.filter;

import com.hengxunda.springcloud.common.constant.C;
import com.hengxunda.springcloud.common.persistence.AjaxResponse;
import com.hengxunda.springcloud.common.security.jwt.AccountJwt;
import com.hengxunda.springcloud.common.security.jwt.JwtUtils;
import com.hengxunda.springcloud.common.utils.Collections3Utils;
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

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthenticationGatewayStrategyRouteFilter extends AbstractGatewayStrategyRouteFilter {

    @Autowired
    private RedisHelper redisHelper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();
        final String upgrade = request.getHeaders().getUpgrade();
        if (StringUtils.equalsIgnoreCase("websocket", upgrade)) {
            return chain.filter(exchange);
        }
        final String path = request.getURI().getPath();
        final List<Object> urls = redisHelper.lGet(C.AUTH_SKIP_URLS_KEY, 0, redisHelper.lGetListSize(C.AUTH_SKIP_URLS_KEY));
        if (Collections3Utils.isNotEmpty(urls.stream().filter(url -> path.contains(url.toString())).collect(Collectors.toList()))) {
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
        final String jwt = headers.getFirst(C.AUTHORIZATION);
        final ServerHttpResponse exchangeResponse = exchange.getResponse();
        if (StringUtils.isEmpty(jwt) || isBlackToken(jwt)) {
            return exchangeResponse.writeWith(Flux.just(exchangeResponse.bufferFactory().wrap(buildExchangeResponse(exchangeResponse, "Authorization Header There is no."))));
        }
        final AccountJwt accountJwt = JwtUtils.parseJwt(jwt, AccountJwt.class);
        if (accountJwt == null) {
            return exchangeResponse.writeWith(Flux.just(exchangeResponse.bufferFactory().wrap(buildExchangeResponse(exchangeResponse, "Invalid request token."))));
        } else {
            final Object o = redisHelper.getObject(C.ROLES_PERMISSION_MAP_KEY);
            if (o == null) {
                return exchangeResponse.writeWith(Flux.just(exchangeResponse.bufferFactory().wrap(buildExchangeResponse(exchangeResponse, "Invalid role key."))));
            } else {
                if (o instanceof Map) {
                    final List<String> permissions = ((Map<String, List<String>>) o).get(accountJwt.getRoles());
                    if (Collections3Utils.isEmpty(permissions.stream().filter(path::contains).collect(Collectors.toList()))) {
                        return exchangeResponse.writeWith(Flux.just(exchangeResponse.bufferFactory().wrap(buildExchangeResponse(exchangeResponse, "Unauthorized request path."))));
                    }
                }
            }
        }
        return chain.filter(exchange);
    }

    private byte[] buildExchangeResponse(final ServerHttpResponse exchangeResponse, final String message) {
        exchangeResponse.setStatusCode(HttpStatus.OK);
        exchangeResponse.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        return FastJsonUtils.toJSONString(AjaxResponse.error(HttpStatus.UNAUTHORIZED.value(), message)).getBytes(StandardCharsets.UTF_8);
    }

    private DataBuffer stringBuffer(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

    private boolean isBlackToken(String jwt) {
        return redisHelper.hasKey(String.format(C.JWT_BLACK_LIST_KEY, jwt));
    }

    @Override
    public int getOrder() {
        return -200;
    }
}
