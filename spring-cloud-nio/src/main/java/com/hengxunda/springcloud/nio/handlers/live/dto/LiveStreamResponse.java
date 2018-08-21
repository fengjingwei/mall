package com.hengxunda.springcloud.nio.handlers.live.dto;

import com.hengxunda.springcloud.nio.handlers.live.utils.DownstreamAddress;
import com.hengxunda.springcloud.nio.handlers.live.utils.LVBChannel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LiveStreamResponse implements Serializable {

    private String channelId;

    private String hlsDownstream;

    private String hlsVoiceDownstream;

    private String flvDownstream;

    private String rtmpDownstream;

    private String streamingStatus;

    public void setDownstream(LVBChannel lvbChannel) {

        channelId = lvbChannel.getChannelId();
        DownstreamAddress address = lvbChannel.getDownstreamAddress();
        flvDownstream = address.getFlvAddress();
        hlsDownstream = address.getHlsAddress();
        rtmpDownstream = address.getRtmpAddress();
        hlsVoiceDownstream = lvbChannel.getHlsVoiceAddress();
    }
}
