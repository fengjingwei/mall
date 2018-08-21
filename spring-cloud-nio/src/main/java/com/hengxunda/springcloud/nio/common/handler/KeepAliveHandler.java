package com.hengxunda.springcloud.nio.common.handler;

import com.hengxunda.springcloud.nio.common.dto.BaseMessage;
import com.hengxunda.springcloud.nio.common.dto.EmptyRequest;
import com.hengxunda.springcloud.nio.common.enums.MsgNoEnum;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

@Component
public class KeepAliveHandler extends AbstractBaseHandler<EmptyRequest> {

    @Override
    public int msgNo() {
        return MsgNoEnum.Sys.KEEP_ALIVE.getCode();
    }

    @Override
    protected Class<EmptyRequest> getRequestBodyClass() {
        return EmptyRequest.class;
    }

    @Override
    protected boolean isNeedCheckAuth() {
        return false;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, EmptyRequest requestBody) {
        return null;
    }

}
