package com.hengxunda.springcloud.nio.handlers.room.dto;

import com.hengxunda.springcloud.common.utils.DateUtils;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class RoomInfoResponse implements Serializable {

    private static final long serialVersionUID = -3102224088126690509L;

    @NonNull
    private String roomId;

    private String name;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    {
        name = "嘟嘟嘟,老司机开车啦";
        startTime = DateUtils.getLocalDateTime();
        endTime = startTime;
    }

}
