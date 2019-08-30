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
public class LiveGetStreamRequest implements Serializable {

    private static final long serialVersionUID = -7995182865067260456L;

    private String type;
}
