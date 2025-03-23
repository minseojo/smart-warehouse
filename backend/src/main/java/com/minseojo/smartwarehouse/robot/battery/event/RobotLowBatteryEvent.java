package com.minseojo.smartwarehouse.robot.battery.event;

import com.minseojo.smartwarehouse.event.BatteryEvent;
import com.minseojo.smartwarehouse.robot.domain.Robot;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RobotLowBatteryEvent implements BatteryEvent {
    private final Robot robot;

    @Override
    public Long getEntityId() {
        return robot.getId();
    }

    @Override
    public double getBatteryPercentage() {
        return robot.getBatteryPercentage();
    }
}
