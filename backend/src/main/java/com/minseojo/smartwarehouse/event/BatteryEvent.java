package com.minseojo.smartwarehouse.event;

public interface BatteryEvent {
    Long getEntityId();
    double getBatteryPercentage();
}
