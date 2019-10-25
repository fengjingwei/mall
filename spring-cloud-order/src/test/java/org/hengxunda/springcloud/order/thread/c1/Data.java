package org.hengxunda.springcloud.order.thread.c1;

public class Data {

    private final int intData;

    public Data(int intData) {
        this.intData = intData;
    }

    public Data(String intData) {
        this.intData = Integer.valueOf(intData);
    }

    public int getData() {
        return intData;
    }

    @Override
    public String toString() {
        return getData() + "";
    }
}
