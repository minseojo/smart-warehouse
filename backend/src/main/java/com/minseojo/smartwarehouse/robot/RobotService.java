package com.minseojo.smartwarehouse.robot;

import com.minseojo.smartwarehouse.robot.domain.Robot;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RobotService {

    private final ApplicationEventPublisher eventPublisher;
    private final RobotRepository robotRepository;

    public Robot findById(long id) {
        return robotRepository.findById(id).orElseThrow();
    }

}
