package com.hengxunda.springcloud.service.common.redis;

public interface CacheManager {

    Object get(final String key);

    Object getSet(final String key, final String value);

    boolean setnx(final String key, final String value);
}
