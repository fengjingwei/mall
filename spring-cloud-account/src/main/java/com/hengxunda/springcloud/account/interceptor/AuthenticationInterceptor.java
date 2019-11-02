package com.hengxunda.springcloud.account.interceptor;

import com.hengxunda.springcloud.common.annotation.Authorization;
import com.hengxunda.springcloud.common.constant.GatewayConstant;
import com.hengxunda.springcloud.common.persistence.AjaxResponse;
import com.hengxunda.springcloud.common.security.jwt.JwtUtils;
import com.hengxunda.springcloud.common.utils.FastJsonUtils;
import com.hengxunda.springcloud.common.utils.StringUtils;
import org.apache.commons.codec.CharEncoding;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setCharacterEncoding(CharEncoding.UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        // 允许跨域的url
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        // 允许的请求方法，一般是GET,POST,PUT,DELETE,OPTIONS
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET,POST,PUT,DELETE,OPTIONS");
        // 允许跨域的请求头
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "X-Requested-With,Content-Type,accept,Auth-Aliw");
        response.addHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
        ServletOutputStream out = response.getOutputStream();
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            final Authorization annotation = method.getAnnotation(Authorization.class);
            if (Objects.isNull(annotation) && Objects.isNull(handlerMethod.getBeanType().getAnnotation(Authorization.class))) {
                return true;
            }
            String jwt = request.getHeader(GatewayConstant.AUTHORIZATION);
            if (StringUtils.isNotBlank(jwt)) {
                try {
                    return JwtUtils.verifyJwt(jwt);
                } catch (Exception e) {
                    out.print(FastJsonUtils.toJSONString(AjaxResponse.error("Invalid request token.")));
                    out.close();
                    return false;
                }
            } else {
                out.print(FastJsonUtils.toJSONString(AjaxResponse.error("Authorization Header There is no.")));
                out.close();
                return false;
            }
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (Objects.isNull(ex)) {
            return;
        }
        Throwable throwable = ex.getCause() == null ? ex : ex.getCause();
        ServletOutputStream out = response.getOutputStream();
        out.print(FastJsonUtils.toJSONString(AjaxResponse.error(throwable.getMessage())));
        out.close();
    }
}