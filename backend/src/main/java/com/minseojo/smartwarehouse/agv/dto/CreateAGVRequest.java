package com.minseojo.smartwarehouse.agv.dto;

import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import com.minseojo.smartwarehouse.agv.entity.AGVMode;
import com.minseojo.smartwarehouse.agv.entity.AGVStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateAGVRequest {
    private String name;
    private double batteryPercentage;
    private Position position;
    private Quaternion rotation;
    private Size size;
    private AGVStatus status; // IDLE, MOVING ...
    private AGVMode mode;   // NORMAL, SAVING ...
    private double speed;
    private Long warehouseId;
}
