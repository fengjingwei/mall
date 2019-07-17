package com.hengxunda.springcloud.order.listener;

import com.hengxunda.springcloud.common.enums.OrderEnum;
import com.hengxunda.springcloud.common.utils.StringUtils;
import com.hengxunda.springcloud.order.entity.Order;
import com.hengxunda.springcloud.order.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.lang.NonNull;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class ExpiredMessageListener extends MessageListenerAdapter {

    private final CountDownLatch countDownLatch;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    public ExpiredMessageListener(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    private static String getOrderNo(String message) {
        final String keyPrefix = "order_";
        if (StringUtils.isNoneBlank(message) && message.contains(keyPrefix)) {
            message = StringUtils.substringAfter(message, keyPrefix);
        }
        return message;
    }

    @Override
    public void onMessage(@NonNull Message message, byte[] pattern) {
        log.info("ExpiredMessageListener -> onMessage -> < {} > key expire.", message);
        Order order = Order.builder().orderNo(getOrderNo(message.toString())).status(OrderEnum.Status.CANCEL.code()).build();
        orderMapper.update(order);
        countDownLatch.countDown();
    }
}
