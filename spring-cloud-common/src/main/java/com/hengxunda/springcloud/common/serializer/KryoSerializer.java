package com.hengxunda.springcloud.common.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.hengxunda.springcloud.common.annotation.SPI;
import com.hengxunda.springcloud.common.enums.SerializeEnum;
import com.hengxunda.springcloud.common.exception.ServiceException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@SPI(value = "kryo")
public class KryoSerializer implements ObjectSerializer {

    @Override
    public byte[] serialize(Object obj) throws ServiceException {
        byte[] bytes;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Kryo kryo = new Kryo();
            Output output = new Output(outputStream);
            kryo.writeObject(output, obj);
            bytes = output.toBytes();
            output.flush();
        } catch (Exception ex) {
            throw new ServiceException("kryo serialize error" + ex.getMessage());
        }
        return bytes;
    }

    @Override
    public <T> T deSerialize(byte[] param, Class<T> clazz) throws ServiceException {
        T object;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(param)) {
            Kryo kryo = new Kryo();
            Input input = new Input(inputStream);
            object = kryo.readObject(input, clazz);
            input.close();
        } catch (Exception e) {
            throw new ServiceException("kryo deSerialize error" + e.getMessage());
        }
        return object;
    }

    @Override
    public String getScheme() {
        return SerializeEnum.KRYO.getSerialize();
    }
}