package com.minseojo.smartwarehouse.robot.dto;

import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import com.minseojo.smartwarehouse.robot.entity.RobotMode;
import com.minseojo.smartwarehouse.robot.entity.RobotStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateRobotRequest {
    private String name;
    private double batteryPercentage;
    private double speed;
    private Position position;
    private Quaternion rotation;
    private Size size;
    private RobotStatus status; // IDLE, MOVING ...
    private RobotMode mode;   // NORMAL, SAVING ...
    private Long warehouseId;
}