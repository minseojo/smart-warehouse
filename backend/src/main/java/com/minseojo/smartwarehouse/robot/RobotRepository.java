package com.minseojo.smartwarehouse.robot;

import com.minseojo.smartwarehouse.robot.domain.Robot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RobotRepository extends JpaRepository<Robot, Long> {

}
