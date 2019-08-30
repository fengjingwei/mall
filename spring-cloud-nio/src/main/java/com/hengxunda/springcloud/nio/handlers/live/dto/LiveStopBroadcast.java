package com.hengxunda.springcloud.nio.handlers.live.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LiveStopBroadcast implements Serializable {

    private static final long serialVersionUID = -2350787368815289483L;

    private String channelId;

    private String type;

    private Long userId;
}
