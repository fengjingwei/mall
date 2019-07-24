package com.hengxunda.springcloud.order.service.impl;

import com.hengxunda.springcloud.common.enums.OrderEnum;
import com.hengxunda.springcloud.common.exception.ServiceException;
import com.hengxunda.springcloud.order.client.AccountClient;
import com.hengxunda.springcloud.order.client.InventoryClient;
import com.hengxunda.springcloud.order.dto.AccountDTO;
import com.hengxunda.springcloud.order.dto.InventoryDTO;
import com.hengxunda.springcloud.order.entity.Order;
import com.hengxunda.springcloud.order.mapper.OrderMapper;
import com.hengxunda.springcloud.order.service.PaymentService;
import com.hengxunda.springcloud.service.common.redis.lock.RedissLockUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Log4j2
@Service
public class PaymentServiceImpl implements PaymentService {

    private final OrderMapper orderMapper;

    // private final AccountRibbon accountRibbon;

    // private final InventoryRibbon inventoryRibbon;

    private final AccountClient accountClient;

    private final InventoryClient inventoryClient;

    /*@Autowired
    public PaymentServiceImpl(OrderMapper orderMapper,
                              AccountRibbon accountRibbon,
                              InventoryRibbon inventoryRibbon) {
        this.orderMapper = orderMapper;
        this.accountRibbon = accountRibbon;
        this.inventoryRibbon = inventoryRibbon;
    }*/

    @Autowired
    public PaymentServiceImpl(OrderMapper orderMapper,
                              AccountClient accountClient,
                              InventoryClient inventoryClient) {
        this.orderMapper = orderMapper;
        this.accountClient = accountClient;
        this.inventoryClient = inventoryClient;
    }

    @Override
    // @Hmily(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    public void makePayment(Order order) {
        order.setStatus(OrderEnum.Status.PAYING.code());
        orderMapper.update(order);
        final BigDecimal accountInfo = accountClient.findByUserId(order.getUserId());
        if (accountInfo.compareTo(order.getTotalAmount()) < 0) {
            throw new ServiceException("余额不足");
        }

        final Integer inventoryInfo = inventoryClient.findByProductId(order.getProductId());
        if (inventoryInfo < order.getCount()) {
            throw new ServiceException("库存不足");
        }

        // 扣除用户余额
        AccountDTO accountDTO = AccountDTO.builder().amount(order.getTotalAmount()).userId(order.getUserId()).build();
        log.info("{}", "===========执行spring cloud扣减资金==========");
        accountClient.payment(accountDTO);

        // 进入扣减库存
        InventoryDTO inventoryDTO = InventoryDTO.builder().count(order.getCount()).productId(order.getProductId()).build();
        log.info("{}", "===========执行spring cloud扣减库存==========");
        final String lockKey = "order:pay:" + order.getOrderNo();
        log.info("lock key : {}", lockKey);
        final boolean b = RedissLockUtils.tryLock(lockKey, 5, 300);
        try {
            if (b) {
                inventoryClient.decrease(inventoryDTO);
            }
        } finally {
            if (b) {
                RedissLockUtils.unlock(lockKey);
            }
        }
    }

    public void confirmOrderStatus(Order order) {
        order.setStatus(OrderEnum.Status.PAY_SUCCESS.code());
        orderMapper.update(order);
        log.info("{}", "===========进行订单confirm操作完成==========");
    }

    public void cancelOrderStatus(Order order) {
        order.setStatus(OrderEnum.Status.PAY_FAIL.code());
        orderMapper.update(order);
        log.info("{}", "===========进行订单cancel操作完成==========");
    }
}
