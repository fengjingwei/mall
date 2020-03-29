package com.hengxunda.springcloud.order.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PayOrderEvent {

    private String orderNo;
}
