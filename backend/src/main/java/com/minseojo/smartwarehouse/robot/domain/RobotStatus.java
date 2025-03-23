package com.minseojo.smartwarehouse.robot.domain;

public enum RobotStatus {
    IDLE,           // 대기 중
    MOVING,         // 이동 중
    CHARGING,       // 충전 중
    WORKING,        // 작업(픽업/드랍 등) 수행 중
    ERROR           // 오류 발생
}
