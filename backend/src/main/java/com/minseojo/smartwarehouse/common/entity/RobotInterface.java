package com.minseojo.smartwarehouse.common.entity;

import com.minseojo.smartwarehouse.common.vo.Position;

public interface RobotInterface {
    void move(Position target);
    double getBatteryPercentage();
}
