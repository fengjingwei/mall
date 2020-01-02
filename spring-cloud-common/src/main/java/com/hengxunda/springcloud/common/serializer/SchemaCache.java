package com.hengxunda.springcloud.common.serializer;

import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class SchemaCache {

    private Cache<Class<?>, Schema<?>> cache = CacheBuilder.newBuilder()
            .maximumSize(1024).expireAfterWrite(1, TimeUnit.HOURS).build();

    protected static SchemaCache getInstance() {
        return SchemaCacheHolder.cache;
    }

    private Schema<?> get(final Class<?> cls, final Cache<Class<?>, Schema<?>> cache) {
        try {
            return cache.get(cls, () -> RuntimeSchema.createFrom(cls));
        } catch (ExecutionException e) {
            return null;
        }
    }

    public Schema<?> get(final Class<?> clazz) {
        return get(clazz, cache);
    }

    private static class SchemaCacheHolder {
        private static SchemaCache cache = new SchemaCache();
    }
}