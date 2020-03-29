package com.hengxunda.springcloud.order.controller;

import com.hengxunda.springcloud.common.persistence.AjaxResponse;
import com.hengxunda.springcloud.order.command.CancelOrderCommand;
import com.hengxunda.springcloud.order.command.CreateOrderCommand;
import com.hengxunda.springcloud.order.command.PayOrderCommand;
import com.hengxunda.springcloud.order.command.bus.CancelOrderCommandBus;
import com.hengxunda.springcloud.order.command.bus.CreateOrderCommandBus;
import com.hengxunda.springcloud.order.command.bus.PayOrderCommandBus;
import com.hengxunda.springcloud.order.dto.OrderDTO;
import com.hengxunda.springcloud.order.entity.Order;
import com.hengxunda.springcloud.order.service.OrderService;
import com.hengxunda.springcloud.order.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@Api(description = "订单管理")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CreateOrderCommand createOrderCommand;

    @Autowired
    private CreateOrderCommandBus createOrderCommandBus;

    @Autowired
    private CancelOrderCommand cancelOrderCommand;

    @Autowired
    private CancelOrderCommandBus cancelOrderCommandBus;

    @Autowired
    private PayOrderCommand payOrderCommand;

    @Autowired
    private PayOrderCommandBus payOrderCommandBus;

    @ApiOperation(value = "获取所有订单")
    @ApiImplicitParam(name = "keyword", value = "关键字", paramType = "query", dataType = "String")
    @GetMapping("listAll")
    public AjaxResponse<List<OrderVO>> listAll(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        return AjaxResponse.success(orderService.listAll(keyword.trim()));
    }

    @ApiOperation(value = "创建订单")
    @ApiImplicitParam(name = "orderDTO", value = "请求对象", required = true, paramType = "body", dataType = "OrderDTO")
    @PostMapping("create")
    public AjaxResponse<Object> create(@RequestBody OrderDTO orderDTO) {
        final Order order = new Order();
        BeanUtils.copyProperties(orderDTO, order);
        return AjaxResponse.success(createOrderCommandBus.dispatch(createOrderCommand, order));
    }

    @ApiOperation(value = "取消订单")
    @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true, paramType = "query", dataType = "String")
    @PostMapping("cancel")
    public AjaxResponse<Object> cancel(@RequestParam(value = "orderNo") String orderNo) {
        return AjaxResponse.success(cancelOrderCommandBus.dispatch(cancelOrderCommand, orderNo));
    }

    @ApiOperation(value = "支付订单【进行扣除账户余额，进行库存扣减】")
    @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true, paramType = "query", dataType = "String")
    @PostMapping("payment")
    public AjaxResponse<Object> payment(@RequestParam(value = "orderNo") String orderNo) {
        return AjaxResponse.success(payOrderCommandBus.dispatch(payOrderCommand, orderNo));
    }
}