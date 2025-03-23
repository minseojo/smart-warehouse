package com.minseojo.smartwarehouse.robot.decision;

import com.minseojo.smartwarehouse.robot.domain.Robot;
import com.minseojo.smartwarehouse.robot.scheduler.RobotAction;
import com.minseojo.smartwarehouse.task.domain.Task;
import org.springframework.stereotype.Component;

@Component
public interface DecisionStrategy {
    RobotAction decide(Robot robot, Task currentTask);
}
