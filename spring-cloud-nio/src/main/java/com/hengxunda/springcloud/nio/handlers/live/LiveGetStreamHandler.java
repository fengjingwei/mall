package com.hengxunda.springcloud.nio.handlers.live;

import com.baidubce.services.lss.model.GetStreamResponse;
import com.hengxunda.springcloud.nio.baidu.BaiduCloudService;
import com.hengxunda.springcloud.nio.common.dto.BaseMessage;
import com.hengxunda.springcloud.nio.common.enums.MsgNoEnum;
import com.hengxunda.springcloud.nio.common.handler.AbstractBaseHandler;
import com.hengxunda.springcloud.nio.handlers.live.dto.LiveGetStreamRequest;
import com.hengxunda.springcloud.nio.handlers.live.dto.LiveStreamResponse;
import com.hengxunda.springcloud.nio.handlers.live.utils.LVBChannel;
import com.hengxunda.springcloud.nio.handlers.live.utils.LiveAddressUtils;
import com.hengxunda.springcloud.nio.handlers.user.LoginHandler;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class LiveGetStreamHandler extends AbstractBaseHandler<LiveGetStreamRequest> {

    @Autowired
    private BaiduCloudService baiduCloudService;

    @Override
    protected Class<LiveGetStreamRequest> getRequestBodyClass() {
        return LiveGetStreamRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, LiveGetStreamRequest requestBody) {
        String roomId = LoginHandler.UserUtils.getRoomId(channel);
        LVBChannel liveInfo = LiveAddressUtils.get(roomId);
        log.info("{}号房间对应的LVBChannel[ {} ]", roomId, liveInfo);

        LiveStreamResponse response = new LiveStreamResponse();
        if (Objects.nonNull(liveInfo)) {
            GetStreamResponse streamResponse = baiduCloudService.getStream("play.ofweek.com", "liveofweek", LiveAddressHandler.LIVE_NAME_PREFIX + roomId);
            if (Objects.nonNull(streamResponse)) {
                response.setStreamingStatus(sllStatus(streamResponse.getStatus()));
                response.setDownstream(liveInfo);
                response.setChannelId(liveInfo.getChannelId());
            }
        }
        return response;
    }

    private String sllStatus(String status) {
        return "ONGOING".equals(status) ? "STREAMING" : status;
    }


    @Override
    public int msgNo() {
        return MsgNoEnum.Live.LIVE_GET_STREAM.getCode();
    }
}
