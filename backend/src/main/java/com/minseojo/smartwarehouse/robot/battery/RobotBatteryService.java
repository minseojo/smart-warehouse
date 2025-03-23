package com.minseojo.smartwarehouse.robot.battery;

import com.minseojo.smartwarehouse.robot.RobotService;
import com.minseojo.smartwarehouse.robot.battery.event.RobotLowBatteryEvent;
import com.minseojo.smartwarehouse.robot.domain.Robot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RobotBatteryService {

    private final ApplicationEventPublisher eventPublisher;
    private final RobotService robotService;

    public void handleLowBattery(Long robotId) {
        Robot robot = robotService.findById(robotId);
        RobotBatteryStatus status = RobotBatteryStatus.from(robot.getBatteryPercentage());

        if (status.shouldReturnToCharger()) {
            // 충전소 이동 로직 등
            log.info("[LOW BATTERY] 로봇 {} 충전소로 이동시킴.", robotId);
        }
    }

    public void updateBattery(Long robotId, double batteryPercentage) {
        Robot robot = robotService.findById(robotId);
        robot.updateBatteryPercentage(batteryPercentage);

        // 도출된 상태에 따라 이벤트 발행
        RobotBatteryStatus status = robot.getBatteryStatus();
        if (status.shouldReturnToCharger()) {
            eventPublisher.publishEvent(new RobotLowBatteryEvent(robot));
        }
    }
}
