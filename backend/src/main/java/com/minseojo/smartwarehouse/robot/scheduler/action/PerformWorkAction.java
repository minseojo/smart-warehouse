package com.minseojo.smartwarehouse.robot.scheduler.action;


import com.minseojo.smartwarehouse.robot.domain.Robot;
import com.minseojo.smartwarehouse.robot.scheduler.RobotAction;
import com.minseojo.smartwarehouse.task.domain.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class PerformWorkAction implements RobotAction {

    private final Task task;

    public PerformWorkAction(Task task) {
        this.task = task;
    }

    @Override
    public void execute(Robot robot) {
        log.info("[ACTION] 로봇 {} → 작업 수행: {}", robot.getId(), task.getDescription());
//        robot.perform(task);
    }
}
