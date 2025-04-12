package com.minseojo.smartwarehouse.warehouse.dto;

import com.minseojo.smartwarehouse.agv.dto.AGVResponse;
import com.minseojo.smartwarehouse.rack.dto.RackResponse;
import com.minseojo.smartwarehouse.warehouse.entity.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class WarehouseStateResponse {
    private List<RackResponse> racks;
    private List<AGVResponse> agvs;


    public static WarehouseStateResponse from(Warehouse warehouse) {
        return new WarehouseStateResponse(
            warehouse.getRacks().stream().map(RackResponse::from).toList(),
            warehouse.getAgvs().stream().map(AGVResponse::from).toList()
        );
    }
}
