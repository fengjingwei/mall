package com.hengxunda.springcloud.common.utils.extension;

import com.hengxunda.springcloud.common.annotation.SPI;
import com.hengxunda.springcloud.common.exception.ServiceException;

import java.util.Objects;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

public class ExtensionLoader<T> {

    private Class<T> type;

    private ExtensionLoader(final Class<T> type) {
        this.type = type;
    }

    private static <T> boolean withExtensionAnnotation(final Class<T> type) {
        return type.isAnnotationPresent(SPI.class);
    }

    public static <T> ExtensionLoader<T> getExtensionLoader(final Class<T> type) {
        if (type == null) {
            throw new ServiceException("Extension type == null");
        }
        if (!type.isInterface()) {
            throw new ServiceException("Extension type(" + type + ") is not interface");
        }
        if (!withExtensionAnnotation(type)) {
            throw new ServiceException("Extension type(" + type + ") is not extension, because WITHOUT @" + SPI.class.getSimpleName() + " Annotation");
        }
        return new ExtensionLoader<>(type);
    }

    public T getActivateExtension(final String value) {
        final ServiceLoader<T> loader = ServiceBootstrap.loadAll(type);
        return StreamSupport.stream(loader.spliterator(), false)
                .filter(e -> Objects.equals(e.getClass().getAnnotation(SPI.class).value(), value))
                .findFirst()
                .orElseThrow(() -> new ServiceException("Please check your configuration"));
    }
}