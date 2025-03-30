package com.minseojo.smartwarehouse.zone.domain;

public enum ZoneType {
    INBOUND,     // 입고
    OUTBOUND,    // 출고
    INBOUND_STORAGE,     // 입고 보관
    OUTBOUND_STORAGE,    // 출고 보관
    CHARGING,    // 충전
    BUFFER,      // 버퍼
    RECEPTION,   // 접수
    PACKING;      // 포장
}
