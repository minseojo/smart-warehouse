package com.minseojo.smartwarehouse.robot.decision;

import com.minseojo.smartwarehouse.robot.RobotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RobotDecisionService {

    private final RobotService robotService;
}
