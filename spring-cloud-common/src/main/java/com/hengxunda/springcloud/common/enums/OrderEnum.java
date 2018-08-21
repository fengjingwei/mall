package com.hengxunda.springcloud.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

public interface OrderEnum {

    @Getter
    @AllArgsConstructor
    enum Status {

        CANCEL(0, "已取消"),

        NOT_PAY(1, "未支付"),

        PAYING(2, "支付中"),

        PAY_FAIL(3, "支付失败"),

        PAY_SUCCESS(4, "支付成功");

        private final int code;

        private final String msg;

        public int code() {
            return code;
        }

        public String msg() {
            return msg;
        }

        public static Status acquire(int code) {
            return Arrays.stream(Status.values()).filter(v -> v.code == code).findFirst().orElse(CANCEL);
        }
    }

}
