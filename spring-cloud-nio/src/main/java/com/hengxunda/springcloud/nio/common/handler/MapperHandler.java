package com.hengxunda.springcloud.nio.common.handler;

import com.google.common.collect.Maps;

import java.util.Map;

public abstract class MapperHandler {

    private static final Map<Integer, Handlebars> msgNoHandlers = Maps.newHashMap();

    public static void addHandler(Handlebars handler) {
        msgNoHandlers.put(handler.msgNo(), handler);
    }

    public static Handlebars getHandler(int msgNo) {
        return msgNoHandlers.get(msgNo);
    }
}
