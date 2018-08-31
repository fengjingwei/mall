package com.hengxunda.springcloud.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum SerializeEnum {

    JDK("jdk"),

    KRYO("kryo"),

    HESSIAN("hessian"),

    PROTOSTUFF("protostuff");

    private String serialize;

    public static SerializeEnum acquire(String serialize) {
        return Arrays.stream(values()).filter(v -> Objects.equals(v.serialize, serialize)).findFirst().orElse(KRYO);
    }

}
