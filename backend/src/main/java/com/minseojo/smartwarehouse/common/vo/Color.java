package com.minseojo.smartwarehouse.common.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Color {

    // Zone 계열
    GREEN("#90EE90"),           // 입고 Zone
    LIGHT_GREEN("#C5E1A5"),     // 입고 보관 Zone
    RED("#EF9A9A"),             // 출고 Zone
    LIGHT_RED("#FFCDD2"),       // 출고 보관 Zone
    BLUE("#90CAF9"),            // 입고-출고 작업 or Info
    YELLOW("#FFF59D"),          // 작업 Zone
    ORANGE("#FFCC80"),          // 출고 보관
    PURPLE("#CE93D8"),          // 로봇 대기장

    // 로봇 & 장비 계열 (New)
    ROBOT_BODY("#81D4FA"),      // 로봇 본체 (연파랑)
    ROBOT_BATTERY_LOW("#FF8A65"), // 로봇 저전력 경고 (연주황)
    ROBOT_BATTERY_OK("#A5D6A7"),  // 로봇 정상 배터리 (연녹색)
    CHARGER("#B39DDB"),         // 충전소

    // 벽, 바닥, 구조물
    OUTER_WALL_GRAY("#90A4AE"),
    INSIDE_WALL_GRAY("#B0BEC5"),
    FLOOR_LIGHT("#DDE4EA"),

    // 랙
    RACK_BODY("#90A4AE"),
    RACK_BORDER("#607D8B"),

    // 박스 (상품)
    BOX_BROWN("#A1887F"),

    // 가이드선, Helper
    LINE_GUIDE("#B0BEC5"),

    // 로봇 Debug용
    DEBUG_PATH("#64B5F6"),

    // 범용 회색
    GRAY("#B0BEC5");

    private final String hex;
}
