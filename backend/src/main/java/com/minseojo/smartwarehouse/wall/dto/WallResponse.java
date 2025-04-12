package com.minseojo.smartwarehouse.wall.dto;

import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import com.minseojo.smartwarehouse.wall.entity.Wall;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@Getter
public class WallResponse {

    private Long id;
    private String description;
    private Position position;
    private Size size;
    private Quaternion rotation;
    private String color;

    public static WallResponse from(Wall wall) {
        return WallResponse.builder()
                .id(wall.getId())
                .description(wall.getDescription())
                .position(wall.getPosition())
                .size(wall.getSize())
                .rotation(wall.getRotation())
                .color(wall.getColor().getHex())
                .build();
    }
}
