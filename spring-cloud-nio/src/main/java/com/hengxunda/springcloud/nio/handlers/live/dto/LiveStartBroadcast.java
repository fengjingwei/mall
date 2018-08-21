package com.hengxunda.springcloud.nio.handlers.live.dto;

import com.hengxunda.springcloud.nio.handlers.live.utils.DownstreamAddress;
import com.hengxunda.springcloud.nio.handlers.live.utils.LVBChannel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LiveStartBroadcast implements Serializable {

    private String type;

    private Long userId;

    private int seek;

    private int page;

    private String channelId;

    private String hlsDownstream;

    private String flvDownstream;

    private String rtmpDownstream;

    private String hlsVoiceDownstream;

    public LiveStartBroadcast(LiveStartRequest requestBody) {
        this.type = requestBody.getType();
    }

    public void setDownstream(LVBChannel lvbChannel) {

        channelId = lvbChannel.getChannelId();
        DownstreamAddress address = lvbChannel.getDownstreamAddress();
        flvDownstream = Objects.nonNull(address) ? address.getFlvAddress() : "";
        hlsDownstream = Objects.nonNull(address) ? address.getHlsAddress() : "";
        rtmpDownstream = Objects.nonNull(address) ? address.getRtmpAddress() : "";
        hlsVoiceDownstream = Objects.nonNull(address) ? lvbChannel.getHlsVoiceAddress() : "";
    }
}
