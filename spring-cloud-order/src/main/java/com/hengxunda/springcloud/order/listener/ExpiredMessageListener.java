package com.hengxunda.springcloud.order.listener;

import com.hengxunda.springcloud.common.enums.OrderEnum;
import com.hengxunda.springcloud.common.utils.StringUtils;
import com.hengxunda.springcloud.order.entity.Order;
import com.hengxunda.springcloud.order.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class ExpiredMessageListener extends MessageListenerAdapter {

    private CountDownLatch countDownLatch;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    public ExpiredMessageListener(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    private static String getOrderNumber(String message) {
        if (StringUtils.isNoneBlank(message) && message.contains("order_")) {
            message = StringUtils.substringAfter(message, "order_");
        }
        return message;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("ExpiredMessageListener -> onMessage -> < {} > key expire.", message);

        Order order = Order.builder().number(getOrderNumber(message.toString())).status(OrderEnum.Status.CANCEL.code()).build();
        orderMapper.update(order);

        countDownLatch.countDown();
    }
}
