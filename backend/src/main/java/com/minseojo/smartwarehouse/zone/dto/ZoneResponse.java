package com.minseojo.smartwarehouse.zone.dto;

import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import com.minseojo.smartwarehouse.zone.domain.Zone;
import com.minseojo.smartwarehouse.zone.domain.ZoneType;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@Getter
public class ZoneResponse {
    private Long id;
    private String name;
    private String description;
    private Position position;
    private Size size;
    private Quaternion rotation;
    private ZoneType type;
    private String color;

    public static ZoneResponse from(Zone zone) {
        return ZoneResponse.builder()
                .id(zone.getId())
                .name(zone.getName())
                .description(zone.getDescription())
                .position(zone.getPosition())
                .size(zone.getSize())
                .rotation(zone.getRotation())
                .type(zone.getType())
                .color(zone.getColor().getHex())
                .build();
    }
}