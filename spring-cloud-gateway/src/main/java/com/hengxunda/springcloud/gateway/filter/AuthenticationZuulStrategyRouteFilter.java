/*
package com.hengxunda.springcloud.gateway.filter;

import com.hengxunda.springcloud.common.constant.C;
import com.hengxunda.springcloud.common.persistence.AjaxResponse;
import com.hengxunda.springcloud.common.utils.FastJsonUtils;
import com.hengxunda.springcloud.common.utils.StringUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Component
public class AuthenticationZuulStrategyRouteFilter extends ZuulFilter {

    */
/**
 * 过滤器的类型,它决定过滤器在请求的哪个生命周期中执行,这里定义为pre,代表会在请求被路由之前执行
 *
 * @return 过滤器的执行顺序, 当请求在一个阶段中存在多个过滤器时, 需要根据该方法返回的值来依次执行
 * @return 判断该过滤器是否需要被执行, 这里我们直接返回了true, 因此该过滤器对所有的请求都生效, 实际运行中我们可以利用该函数来指定过滤器的有效范围
 * @return 过滤器的具体执行逻辑
 * @return 过滤器的执行顺序, 当请求在一个阶段中存在多个过滤器时, 需要根据该方法返回的值来依次执行
 * @return 判断该过滤器是否需要被执行, 这里我们直接返回了true, 因此该过滤器对所有的请求都生效, 实际运行中我们可以利用该函数来指定过滤器的有效范围
 * @return 过滤器的具体执行逻辑
 * @return 过滤器的执行顺序, 当请求在一个阶段中存在多个过滤器时, 需要根据该方法返回的值来依次执行
 * @return 判断该过滤器是否需要被执行, 这里我们直接返回了true, 因此该过滤器对所有的请求都生效, 实际运行中我们可以利用该函数来指定过滤器的有效范围
 * @return 过滤器的具体执行逻辑
 * @return 过滤器的执行顺序, 当请求在一个阶段中存在多个过滤器时, 需要根据该方法返回的值来依次执行
 * @return 判断该过滤器是否需要被执行, 这里我们直接返回了true, 因此该过滤器对所有的请求都生效, 实际运行中我们可以利用该函数来指定过滤器的有效范围
 * @return 过滤器的具体执行逻辑
 * @return 过滤器的执行顺序, 当请求在一个阶段中存在多个过滤器时, 需要根据该方法返回的值来依次执行
 * @return 判断该过滤器是否需要被执行, 这里我们直接返回了true, 因此该过滤器对所有的请求都生效, 实际运行中我们可以利用该函数来指定过滤器的有效范围
 * @return 过滤器的具体执行逻辑
 * @return 过滤器的执行顺序, 当请求在一个阶段中存在多个过滤器时, 需要根据该方法返回的值来依次执行
 * @return 判断该过滤器是否需要被执行, 这里我们直接返回了true, 因此该过滤器对所有的请求都生效, 实际运行中我们可以利用该函数来指定过滤器的有效范围
 * @return 过滤器的具体执行逻辑
 * @return
 *//*

    @Override
    public String filterType() {
        return "pre";
    }

    */
/**
 * 过滤器的执行顺序,当请求在一个阶段中存在多个过滤器时,需要根据该方法返回的值来依次执行
 *
 * @return
 *//*

    @Override
    public int filterOrder() {
        return 0;
    }

    */
/**
 * 判断该过滤器是否需要被执行,这里我们直接返回了true,因此该过滤器对所有的请求都生效,实际运行中我们可以利用该函数来指定过滤器的有效范围
 *
 * @return
 *//*

    @Override
    public boolean shouldFilter() {
        return true;
    }

    */
/**
 * 过滤器的具体执行逻辑
 *
 * @return
 *//*

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        log.info("send {} request to {}", request.getMethod(), request.getRequestURI());
        String jwt = request.getHeader(C.AUTHORIZATION);
        if (StringUtils.isNotBlank(jwt)) {
            context.setSendZuulResponse(true);
            context.setResponseStatusCode(HttpStatus.SC_OK);
            context.set("isSuccess", true);
        } else {
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            context.setResponseBody(FastJsonUtils.toJSONString(AjaxResponse.error(HttpStatus.SC_UNAUTHORIZED, "Authorization Header There is no.")));
            context.set("isSuccess", false);
        }
        return null;
    }
}*/
