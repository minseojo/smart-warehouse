package com.minseojo.smartwarehouse.robot.scheduler;

import com.minseojo.smartwarehouse.robot.domain.Robot;

public interface RobotAction {
    void execute(Robot robot);
}
