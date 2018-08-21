package com.hengxunda.springcloud.nio.handlers.live.utils;

import lombok.Data;

@Data
public class DownstreamAddress {

    private String hlsAddress;

    private String rtmpAddress;

    private String flvAddress;
}
