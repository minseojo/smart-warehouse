package com.minseojo.smartwarehouse.robot.scheduler.action;

import com.minseojo.smartwarehouse.robot.domain.Robot;
import com.minseojo.smartwarehouse.robot.scheduler.RobotAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MoveToChargerAction implements RobotAction {

    @Override
    public void execute(Robot robot) {
        log.info("[ACTION] 로봇 {} → 충전소로 이동", robot.getId());
        // TODO: 충전소 이동 로직
//        robot.moveToNearestCharger();
    }
}
