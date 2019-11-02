package com.hengxunda.springcloud.common.persistence;

public interface CommandBus {

    <T> Object dispatch(Command command, T model);
}