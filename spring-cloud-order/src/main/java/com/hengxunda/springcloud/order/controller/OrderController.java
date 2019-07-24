package com.hengxunda.springcloud.order.controller;

import com.hengxunda.springcloud.common.annotation.Authorization;
import com.hengxunda.springcloud.common.enums.OrderEnum;
import com.hengxunda.springcloud.common.exception.ServiceException;
import com.hengxunda.springcloud.common.persistence.AjaxResponse;
import com.hengxunda.springcloud.order.dto.OrderDTO;
import com.hengxunda.springcloud.order.entity.Order;
import com.hengxunda.springcloud.order.rabbitmq.producers.OrderProducer;
import com.hengxunda.springcloud.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Api(description = "订单管理")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderProducer orderProducer;

    @ApiOperation(value = "获取所有的订单")
    @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query", dataType = "String")
    @GetMapping("listAll")
    public AjaxResponse listAll(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        return AjaxResponse.success(orderService.listAll(keyword.trim()));
    }

    @Authorization
    @ApiOperation(value = "创建订单")
    @ApiImplicitParam(name = "orderDTO", value = "订单请求对象", required = true, paramType = "body", dataType = "OrderDTO")
    @PostMapping("create")
    public AjaxResponse create(@RequestBody OrderDTO orderDTO) {
        Order order = new Order();
        BeanUtils.copyProperties(orderDTO, order);
        return AjaxResponse.success(orderService.save(order));
    }

    @ApiOperation(value = "订单支付并进行扣除账户余额，进行库存扣减")
    @PostMapping(value = "orderPay")
    public AjaxResponse orderPay(@RequestParam(value = "orderNo") String orderNo) {
        final Order order = orderService.get(orderNo);
        if (order == null) {
            throw new ServiceException("订单不存在");
        }
        final OrderEnum.Status status = OrderEnum.Status.acquireByCode(order.getStatus());
        if (status == OrderEnum.Status.CANCEL) {
            throw new ServiceException("订单已取消");
        }
        if (status == OrderEnum.Status.PAY_SUCCESS) {
            throw new ServiceException("订单重复支付");
        }
        orderProducer.orderPay(Order.builder().orderNo(orderNo).build());
        return AjaxResponse.success();
    }

}
