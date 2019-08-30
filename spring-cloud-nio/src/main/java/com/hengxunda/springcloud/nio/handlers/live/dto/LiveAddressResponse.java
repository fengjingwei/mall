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
public class LiveAddressResponse implements Serializable {

    private static final long serialVersionUID = -2939152838506553543L;

    private String upstreamAddress;

    private String channelId;
}
