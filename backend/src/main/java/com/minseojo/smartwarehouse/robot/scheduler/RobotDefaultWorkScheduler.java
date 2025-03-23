package com.minseojo.smartwarehouse.robot.scheduler;

import com.minseojo.smartwarehouse.robot.decision.DecisionStrategy;
import com.minseojo.smartwarehouse.robot.domain.Robot;
import com.minseojo.smartwarehouse.task.domain.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RobotDefaultWorkScheduler implements RobotWorkScheduler {

    private final DecisionStrategy decisionStrategy;

    @Override
    public void scheduleWork(Robot robot, Task task) {
        RobotAction action = decisionStrategy.decide(robot, task);
        action.execute(robot);
    }

    @Override
    public void assignWork(Task task, List<Robot> robots) {
        Robot robot = selectBestRobot(task, robots);
        RobotAction action = decisionStrategy.decide(robot, task);
        action.execute(robot);
    }

    @Override
    public void cancelWork(Robot robot, Task task) {

    }

    @Override
    public Robot selectBestRobot(Task task, List<Robot> robots) {
        return null;
    }
}

