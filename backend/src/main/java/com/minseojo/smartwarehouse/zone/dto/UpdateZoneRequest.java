package com.minseojo.smartwarehouse.zone.dto;

import com.minseojo.smartwarehouse.common.vo.Color;
import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import com.minseojo.smartwarehouse.zone.domain.ZoneType;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of", access = AccessLevel.PUBLIC)
@Builder
@Getter
public class UpdateZoneRequest {
    private String name;
    private String description;
    private Position position;
    private Size size;
    private Quaternion rotation;
    private ZoneType type;
    private Color color;
}