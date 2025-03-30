package com.minseojo.smartwarehouse.rack.dto;

import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import com.minseojo.smartwarehouse.rack.domain.Rack;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of", access = AccessLevel.PUBLIC)
@Builder
@Getter
public class RackResponse {
    private Long id;
    private String name;
    private String description;
    private Position position;
    private Size size;
    private Quaternion rotation;
    private Long zoneId;

    public static RackResponse from(Rack rack) {
        return RackResponse.builder()
                .id(rack.getId())
                .name(rack.getName())
                .description(rack.getDescription())
                .position(rack.getPosition())
                .size(rack.getSize())
                .rotation(rack.getRotation())
                .zoneId(rack.getZone().getId())
                .build();
    }
}