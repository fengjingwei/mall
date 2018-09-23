package com.hengxunda.springcloud.common.serializer;

import com.hengxunda.springcloud.common.exception.ServiceException;

public interface ObjectSerializer {

    /**
     * 序列化
     *
     * @param obj
     * @return
     * @throws ServiceException
     */
    byte[] serialize(Object obj) throws ServiceException;

    /**
     * 反序列化
     *
     * @param param
     * @param clazz
     * @param <T>
     * @return
     * @throws ServiceException
     */
    <T> T deSerialize(byte[] param, Class<T> clazz) throws ServiceException;

    /**
     * scheme
     *
     * @return
     */
    String getScheme();
}
