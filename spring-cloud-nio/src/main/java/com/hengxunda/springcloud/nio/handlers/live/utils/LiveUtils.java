package com.hengxunda.springcloud.nio.handlers.live.utils;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class LiveUtils {

    private static Map<String, RoomLiveStream> roomLives = Maps.newConcurrentMap();

    public static void put(RoomLiveStream stream) {
        if (!roomLives.containsKey(stream.getRoomId())) {
            roomLives.put(stream.getRoomId(), stream);
        }
    }

    public static RoomLiveStream get(String roomId) {
        return roomLives.get(roomId);
    }

    public static void remove(String roomId) {
        roomLives.remove(roomId);
    }

    public static boolean isPlaying(String roomId) {
        return Objects.nonNull(get(roomId));
    }

    public static Set<String> getLiveRoomIds() {
        return Sets.newHashSet(roomLives.keySet());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RoomLiveStream {

        private String socketId;

        private Long userId;

        private String roomId;

        private LVBChannel liveInfo;

        private String type;

        private boolean playing;

        private Date playTime;
    }
}
