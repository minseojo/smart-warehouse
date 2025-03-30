package com.minseojo.smartwarehouse.robot.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RobotStatus {
    IDLE("Idle"),
    MOVING("Moving"),
    CHARGING("Charging"),
    WORKING("Working"),
    ERROR("Error");

    private final String name;

    public boolean isIdle() { return this == IDLE; }
    public boolean isMoving() { return this == MOVING; }
    public boolean isCharging() { return this == CHARGING; }
    public boolean isWorking() { return this == WORKING; }
    public boolean isError() { return this == ERROR; }
}

