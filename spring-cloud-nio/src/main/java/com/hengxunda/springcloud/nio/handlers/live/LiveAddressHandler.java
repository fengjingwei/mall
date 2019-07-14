package com.hengxunda.springcloud.nio.handlers.live;

import com.baidubce.services.lss.model.CreateStreamResponse;
import com.baidubce.services.lss.model.LivePlay;
import com.hengxunda.springcloud.nio.baidu.BaiduCloudService;
import com.hengxunda.springcloud.nio.common.dto.BaseMessage;
import com.hengxunda.springcloud.nio.common.dto.EmptyRequest;
import com.hengxunda.springcloud.nio.common.enums.MsgNoEnum;
import com.hengxunda.springcloud.nio.common.handler.AbstractBaseHandler;
import com.hengxunda.springcloud.nio.handlers.live.dto.LiveAddressResponse;
import com.hengxunda.springcloud.nio.handlers.live.utils.DownstreamAddress;
import com.hengxunda.springcloud.nio.handlers.live.utils.LVBChannel;
import com.hengxunda.springcloud.nio.handlers.live.utils.LiveAddressUtils;
import com.hengxunda.springcloud.nio.handlers.user.LoginHandler;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LiveAddressHandler extends AbstractBaseHandler<EmptyRequest> {

    public static final String LIVE_NAME_PREFIX = "ofweekLiveRoom_";

    @Autowired
    private BaiduCloudService baiduCloudService;

    @Override
    protected Class<EmptyRequest> getRequestBodyClass() {
        return EmptyRequest.class;
    }

    @Override
    protected Object doProcess(Channel channel, BaseMessage request, EmptyRequest requestBody) {
        String roomId = LoginHandler.UserUtils.getRoomId(channel);
        LVBChannel liveInfo = LiveAddressUtils.get(roomId);
        if (Objects.isNull(liveInfo)) {
            CreateStreamResponse sessionResponse = baiduCloudService.createPushStream("play.ofweek.com", "liveofweek", LIVE_NAME_PREFIX + roomId);
            liveInfo = newLVBChannel(sessionResponse);
            LiveAddressUtils.put(roomId, liveInfo);
        }
        LiveAddressResponse response = new LiveAddressResponse();
        response.setUpstreamAddress(liveInfo.getUpstreamAddress());
        response.setChannelId(liveInfo.getChannelId());
        return response;
    }

    @Override
    public int msgNo() {
        return MsgNoEnum.Live.LIVE_ADDRESS.getCode();
    }

    private LVBChannel newLVBChannel(CreateStreamResponse streamResponse) {
        LVBChannel channel = new LVBChannel();
        channel.setChannelId(streamResponse.getSessionId());
        channel.setUpstreamAddress(streamResponse.getPublish().getPushUrl());

        LivePlay playInfo = streamResponse.getPlay();
        DownstreamAddress address = new DownstreamAddress();
        address.setFlvAddress(playInfo.getFlvUrls().get("L0"));
        address.setHlsAddress(playInfo.getHlsUrls().get("L0"));
        address.setRtmpAddress(playInfo.getRtmpUrls().get("L0"));

        channel.setDownstreamAddress(address);
        channel.setHlsVoiceAddress(playInfo.getHlsUrls().get("L1"));
        return channel;
    }
}
