package com.minseojo.smartwarehouse.robot.battery.strategy;

import com.minseojo.smartwarehouse.robot.domain.Robot;

public interface RobotBatteryStrategy {
    void handle(Robot robot);
}