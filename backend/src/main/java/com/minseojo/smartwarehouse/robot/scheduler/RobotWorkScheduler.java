package com.minseojo.smartwarehouse.robot.scheduler;

import com.minseojo.smartwarehouse.robot.domain.Robot;
import com.minseojo.smartwarehouse.task.domain.Task;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RobotWorkScheduler {
    void scheduleWork(Robot robot, Task task);
    Robot selectBestRobot(Task task, List<Robot> robots);
    void assignWork(Task task, List<Robot> robots);
    void cancelWork(Robot robot, Task task);
}
