package com.hengxunda.springcloud.nio.handlers.live.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LiveStartRequest implements Serializable {

    private static final long serialVersionUID = 8247106611573520111L;

    private String type;
}
