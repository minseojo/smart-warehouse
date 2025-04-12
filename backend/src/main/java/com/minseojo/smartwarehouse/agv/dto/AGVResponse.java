package com.minseojo.smartwarehouse.agv.dto;

import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import com.minseojo.smartwarehouse.agv.entity.AGV;
import com.minseojo.smartwarehouse.agv.entity.AGVMode;
import com.minseojo.smartwarehouse.agv.entity.AGVStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AGVResponse {
    private Long id;            // 서버에서 관리하는 로봇 식별자
    private String name;        // 사용자에게 보여줄 로봇 이름
    private double batteryPercentage;
    private Position position;
    private Quaternion rotation;
    private Size size;
    private double speed;
    private AGVStatus status;
    private AGVMode mode;

    public static AGVResponse from(AGV AGV) {
        return AGVResponse.builder()
                .id(AGV.getId())
                .name(AGV.getName())
                .batteryPercentage(AGV.getBatteryPercentage())
                .position(Position.of(AGV.getPosition()))
                .rotation(Quaternion.of(AGV.getRotation()))
                .size(Size.of(AGV.getSize()))
                .speed(AGV.getSpeed())
                .status(AGV.getStatus())
                .mode(AGV.getMode())
                .build();
    }

}
