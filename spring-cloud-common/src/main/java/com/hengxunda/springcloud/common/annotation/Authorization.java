package com.hengxunda.springcloud.common.annotation;

import java.lang.annotation.*;

/**
 * 方法上加上此注解,说明需要登录才能访问
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Authorization {

}
