package com.minseojo.smartwarehouse.robot.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RobotMode {
    NORMAL("Normal"), // 정상 모드 (작업 수행)
    SAVING("Saving"), // 절전 모드 (속도 낮춤, 작업 최소화)
    RETURN_TO_CHARGER("Return To Charger");  // 충전기 방향으로 이동 중

    private final String name;

    public boolean isNormal() { return this == NORMAL; }
    public boolean isSaving() { return this == SAVING; }
    public boolean isReturnToCharger() { return this == RETURN_TO_CHARGER; }
}
