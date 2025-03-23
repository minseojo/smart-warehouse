package com.minseojo.smartwarehouse.robot.decision;

import com.minseojo.smartwarehouse.robot.battery.RobotBatteryStatus;
import com.minseojo.smartwarehouse.robot.domain.Robot;
import com.minseojo.smartwarehouse.robot.scheduler.RobotAction;
import com.minseojo.smartwarehouse.robot.scheduler.action.MoveToChargerAction;
import com.minseojo.smartwarehouse.robot.scheduler.action.PerformWorkAction;
import com.minseojo.smartwarehouse.task.domain.Task;

public class BatteryBasedDecisionStrategy implements DecisionStrategy {

    @Override
    public RobotAction decide(Robot robot, Task currentTask) {
        RobotBatteryStatus status = RobotBatteryStatus.from(robot.getBatteryPercentage());

        if (status.shouldReturnToCharger()) {
            return new MoveToChargerAction();
        } else {
//            return new PerformTaskAction(task);
            return new PerformWorkAction(currentTask);
        }
    }
}