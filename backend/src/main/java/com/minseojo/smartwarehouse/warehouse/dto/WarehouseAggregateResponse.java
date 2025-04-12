package com.minseojo.smartwarehouse.warehouse.dto;

import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import com.minseojo.smartwarehouse.rack.dto.RackResponse;
import com.minseojo.smartwarehouse.wall.dto.WallResponse;
import com.minseojo.smartwarehouse.warehouse.entity.Warehouse;
import com.minseojo.smartwarehouse.zone.dto.ZoneResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class WarehouseAggregateResponse {
    private String name;
    private String description;
    private Position position;
    private Size size;
    private Quaternion rotation;

    private List<WallResponse> walls;
    private List<ZoneResponse> zones;
    private List<RackResponse> racks;

    public static WarehouseAggregateResponse from(Warehouse warehouse) {
        return new WarehouseAggregateResponse(
                warehouse.getName(),
                warehouse.getDescription(),
                warehouse.getPosition(),
                warehouse.getSize(),
                warehouse.getRotation(),
                warehouse.getWalls().stream().map(WallResponse::from).toList(),
                warehouse.getZones().stream().map(ZoneResponse::from).toList(),
                warehouse.getRacks().stream().map(RackResponse::from).toList()
        );
    }
}
