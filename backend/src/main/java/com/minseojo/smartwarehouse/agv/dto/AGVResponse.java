package com.minseojo.smartwarehouse.agv.dto;

import com.minseojo.smartwarehouse.agv.entity.AGV;
import com.minseojo.smartwarehouse.agv.entity.AGVMode;
import com.minseojo.smartwarehouse.agv.entity.AGVStatus;
import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AGVResponse {

    private Long id;                    // 서버에서 관리하는 AGV 식별자
    private String name;                // 사용자에게 보여줄 AGV 이름
    private double batteryPercentage;   // 배터리 상태
    private Position position;          // 위치
    private Quaternion rotation;        // 회전 정보
    private Size size;                  // AGV 크기
    private double speed;               // 이동 속도
    private AGVStatus status;           // 현재 상태
    private AGVMode mode;               // 현재 모드 (노말/베터리 절약 등)


    public static AGVResponse from(AGV agv) {
        return AGVResponse.builder()
                .id(agv.getId())
                .name(agv.getName())
                .batteryPercentage(agv.getBatteryPercentage())
                .position(agv.getPosition() != null ? Position.of(agv.getPosition()) : new Position(0, 0, 0))
                .rotation(agv.getRotation() != null ? Quaternion.of(agv.getRotation()) : Quaternion.identity())
                .size(agv.getSize() != null ? Size.of(agv.getSize()) : new Size(100, 50, 50))
                .speed(agv.getSpeed())
                .status(agv.getStatus())
                .mode(agv.getMode())
                .build();
    }
}
