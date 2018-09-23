package com.hengxunda.springcloud.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

public abstract class FastJsonUtils {

    private static final SerializeConfig CONFIG;

    private static final SerializerFeature[] FEATURES = {
            // 输出空置字段
            SerializerFeature.WriteMapNullValue,
            // list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullListAsEmpty,
            // 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullNumberAsZero,
            // Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullBooleanAsFalse,
            // 字符类型字段如果为null，输出为""，而不是null
            SerializerFeature.WriteNullStringAsEmpty
    };

    static {
        CONFIG = new SerializeConfig();
        // 使用和json-lib兼容的日期输出格式
        // CONFIG.put(java.util.Date.class, new JSONLibDataFormatSerializer());
        // 使用和json-lib兼容的日期输出格式
        // CONFIG.put(java.sql.Date.class, new JSONLibDataFormatSerializer());
    }

    public static <T> T parseObject(String text, Class<T> type) {
        return JSON.parseObject(text, type);
    }

    public static String toJSONString(Object value) {
        return JSON.toJSONString(value, CONFIG, FEATURES);
    }

    public static <T> List<T> toList(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

}
