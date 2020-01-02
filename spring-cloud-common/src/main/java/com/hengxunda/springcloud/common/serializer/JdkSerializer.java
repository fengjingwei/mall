package com.hengxunda.springcloud.common.serializer;

import com.hengxunda.springcloud.common.annotation.SPI;
import com.hengxunda.springcloud.common.enums.SerializeEnum;
import com.hengxunda.springcloud.common.exception.ServiceException;

import java.io.*;

@SPI(value = "jdk")
public class JdkSerializer implements ObjectSerializer {

    @Override
    public byte[] serialize(final Object obj) throws ServiceException {
        try (ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream(); ObjectOutput objectOutput = new ObjectOutputStream(arrayOutputStream)) {
            objectOutput.writeObject(obj);
            objectOutput.flush();
            return arrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new ServiceException("java serialize error " + e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deSerialize(final byte[] param, final Class<T> clazz) throws ServiceException {
        try (ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(param); ObjectInput input = new ObjectInputStream(arrayInputStream)) {
            return (T) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new ServiceException("java deSerialize error " + e.getMessage());
        }
    }

    @Override
    public String getScheme() {
        return SerializeEnum.JDK.getSerialize();
    }
}