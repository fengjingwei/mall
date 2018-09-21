package com.hengxunda.springcloud.nio.handlers.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivateChatRequest implements Serializable {

    private String roomId;

    private String content;

    private Long senderId;

    private Long receivedId;

    private Date createTime;
}
