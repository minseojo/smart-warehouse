package com.minseojo.smartwarehouse.robot.battery.handler;

import com.minseojo.smartwarehouse.robot.battery.RobotBatteryService;
import com.minseojo.smartwarehouse.robot.battery.event.RobotFullBatteryEvent;
import com.minseojo.smartwarehouse.robot.battery.event.RobotLowBatteryEvent;
import com.minseojo.smartwarehouse.robot.domain.Robot;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RobotBatteryEventHandler {

    private final RobotBatteryService robotBatteryService;

    @EventListener
    public void handleLowBattery(RobotLowBatteryEvent event) {
//        Robot robot = event.getEntityId();
        // 충전소로 이동 명령, 상태 변경 등
    }

    @EventListener
    public void handleFullBattery(RobotFullBatteryEvent event) {
//        Robot robot = event.getRobot();
        // 작업 복귀, 고속 모드 전환 등
    }
}
