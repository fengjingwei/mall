package com.hengxunda.springcloud.nio.handlers.live.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LVBChannel {

    private String channelId;

    private String upstreamAddress;

    private DownstreamAddress downstreamAddress;

    private String hlsVoiceAddress;
}
