package com.minseojo.smartwarehouse.robot.dto;

import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import com.minseojo.smartwarehouse.robot.domain.Robot;
import com.minseojo.smartwarehouse.robot.domain.RobotMode;
import com.minseojo.smartwarehouse.robot.domain.RobotStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RobotResponse {
    private Long id;            // 서버에서 관리하는 로봇 식별자
    private String name;        // 사용자에게 보여줄 로봇 이름
    private double batteryPercentage;
    private Position position;
    private Quaternion rotation;
    private Size size;
    private double speed;
    private RobotStatus status;
    private RobotMode mode;

    public static RobotResponse from(Robot robot) {
        return RobotResponse.builder()
                .id(robot.getId())
                .name(robot.getName())
                .batteryPercentage(robot.getBatteryPercentage())
                .position(Position.of(robot.getPosition()))
                .rotation(Quaternion.of(robot.getRotation()))
                .size(Size.of(robot.getSize()))
                .speed(robot.getSpeed())
                .status(robot.getStatus())
                .mode(robot.getMode())
                .build();
    }

}
