package com.hengxunda.springcloud.order.command.bus;

import com.hengxunda.springcloud.common.persistence.Command;
import com.hengxunda.springcloud.common.persistence.CommandBus;
import org.springframework.stereotype.Component;

@Component
public class OrderPayCommandBus implements CommandBus {

    @Override
    public <T> Object dispatch(Command command, T model) {
        return command.execute(model);
    }
}
