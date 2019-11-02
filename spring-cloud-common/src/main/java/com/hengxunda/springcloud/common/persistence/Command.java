package com.hengxunda.springcloud.common.persistence;

public interface Command<T> {

    Object execute(T model);
}
