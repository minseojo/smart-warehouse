package com.minseojo.smartwarehouse.warehouse.dto;

import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import com.minseojo.smartwarehouse.warehouse.entity.Warehouse;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WarehouseResponse {
    private String name;
    private String description;
    private Position position;
    private Quaternion rotation;
    private Size size;

    public static WarehouseResponse from(Warehouse warehouse) {
        return WarehouseResponse.builder()
                .name(warehouse.getName())
                .description(warehouse.getDescription())
                .position(Position.of(warehouse.getPosition()))
                .rotation(Quaternion.of(warehouse.getRotation()))
                .size(Size.of(warehouse.getSize()))
                .build();
    }
}
