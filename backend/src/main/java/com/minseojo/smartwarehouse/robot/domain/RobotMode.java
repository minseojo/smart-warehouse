package com.minseojo.smartwarehouse.robot.domain;

public enum RobotMode {
    NORMAL,             // 정상 모드 (작업 수행)
    SAVING,             // 절전 모드 (속도 낮춤, 작업 최소화)
    RETURN_TO_CHARGER   // 충전기 방향으로 이동 중
}
