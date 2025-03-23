package com.minseojo.smartwarehouse.robot.battery;

import com.minseojo.smartwarehouse.robot.battery.strategy.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RobotBatteryStatus {
    FULL(new RobotFullBatteryStrategy()),
    HIGH(new RobotHighBatteryStrategy()),
    MEDIUM(new RobotMediumBatteryStrategy()),
    LOW(new RobotLowBatteryStrategy()),
    CRITICAL(new RobotCriticalBatteryStrategy());

    private final RobotBatteryStrategy strategy;

    public static RobotBatteryStatus from(double battery) {
        if (battery >= 90.0) return FULL;
        else if (battery >= 70.0) return HIGH;
        else if (battery >= 40.0) return MEDIUM;
        else if (battery >= 15.0) return LOW;
        else return CRITICAL;
    }

    public boolean allowsHighSpeedMode() {
        return this == FULL || this == HIGH;
    }

    public boolean shouldReturnToCharger() {
        return this == LOW ||  this == CRITICAL;
    }

    public boolean isCritical() {
        return this == CRITICAL;
    }

}
