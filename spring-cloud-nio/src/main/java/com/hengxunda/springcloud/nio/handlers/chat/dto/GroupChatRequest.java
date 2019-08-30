package com.hengxunda.springcloud.nio.handlers.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupChatRequest implements Serializable {

    private static final long serialVersionUID = -5512272062049036159L;

    private String roomId;

    private String content;

    private Long senderId;

    private Date createTime;
}
