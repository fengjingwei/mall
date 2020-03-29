package com.hengxunda.springcloud.order.events;

import com.hengxunda.springcloud.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateOrderEvent {

    private Order order;
}
