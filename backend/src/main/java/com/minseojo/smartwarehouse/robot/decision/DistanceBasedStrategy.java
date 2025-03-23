package com.minseojo.smartwarehouse.robot.decision;

import com.minseojo.smartwarehouse.robot.domain.Robot;
import com.minseojo.smartwarehouse.robot.scheduler.RobotAction;
import com.minseojo.smartwarehouse.task.domain.Task;

public class DistanceBasedStrategy implements DecisionStrategy {

    @Override
    public RobotAction decide(Robot robot, Task currentTask) {
        return null;
    }
}
