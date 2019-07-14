package com.hengxunda.springcloud.account.interceptor;

import com.hengxunda.springcloud.common.annotation.Authorization;
import com.hengxunda.springcloud.common.persistence.AjaxResponse;
import com.hengxunda.springcloud.common.security.jwt.AccountJWT;
import com.hengxunda.springcloud.common.security.jwt.JwtUtils;
import com.hengxunda.springcloud.common.utils.FastJsonUtils;
import com.hengxunda.springcloud.common.utils.StringUtils;
import org.apache.commons.codec.CharEncoding;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static final String AUTH_TOKEN = "authToken";

    private static final String JWT_SESSION_KEY = "JWT-SESSION-KEY";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setCharacterEncoding(CharEncoding.UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        // 允许跨域的url
        response.addHeader("Access-Control-Allow-Origin", "*");
        // 允许的请求方法，一般是GET,POST,PUT,DELETE,OPTIONS
        response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        // 允许跨域的请求头
        response.addHeader("Access-Control-Allow-Headers", "X-Requested-With,Content-Type,accept,Auth-Aliw");
        response.addHeader("Access-Control-Max-Age", "3600");
        ServletOutputStream out = response.getOutputStream();

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            final Authorization annotation = method.getAnnotation(Authorization.class);
            if (Objects.isNull(annotation) && Objects.isNull(handlerMethod.getBeanType().getAnnotation(Authorization.class))) {
                return true;
            }

            String authToken = request.getHeader(AUTH_TOKEN);
            if (StringUtils.isNotBlank(authToken)) {
                try {
                    final AccountJWT accountJWT = JwtUtils.parseJWT(authToken);
                    if (Objects.nonNull(accountJWT)) {
                        request.setAttribute(JWT_SESSION_KEY, accountJWT);
                        return true;
                    }
                } catch (Exception e) {
                    out.print(FastJsonUtils.toJSONString(AjaxResponse.error("Request token timeout.")));
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
