package com.hengxunda.springcloud.nio.common.handler;

import com.google.common.collect.Maps;

import java.util.Map;

public abstract class MapperHandler {

    private static final Map<Integer, Handlebars> MSG_NO_HANDLERS = Maps.newHashMap();

    public static void addHandler(Handlebars handler) {
        MSG_NO_HANDLERS.put(handler.msgNo(), handler);
    }

    public static Handlebars getHandler(int msgNo) {
        return MSG_NO_HANDLERS.get(msgNo);
    }
}
