package com.minseojo.smartwarehouse.charger.dto;

import com.minseojo.smartwarehouse.charger.domain.Charger;
import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChargerResponse {

    private Long id;
    private String name;
    private Position position;
    private Size size;
    private Quaternion rotation;
    private Long zoneId;

    public static ChargerResponse from(Charger charger) {
        return ChargerResponse.builder()
                .id(charger.getId())
                .name(charger.getName())
                .position(charger.getPosition())
                .size(charger.getSize())
                .rotation(charger.getRotation())
                .zoneId(charger.getZone().getId())
                .build();
    }
}
