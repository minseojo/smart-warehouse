package com.minseojo.smartwarehouse.robot;

import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import com.minseojo.smartwarehouse.device.DeviceType;
import com.minseojo.smartwarehouse.robot.entity.Robot;
import com.minseojo.smartwarehouse.robot.entity.RobotMode;
import com.minseojo.smartwarehouse.robot.entity.RobotStatus;
import com.minseojo.smartwarehouse.robot.dto.CreateRobotRequest;
import com.minseojo.smartwarehouse.robot.dto.RobotResponse;
import com.minseojo.smartwarehouse.robot.dto.UpdateRobotRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RobotService {

    private final RobotRepository robotRepository;

    @Transactional(readOnly = true)
    public Robot getByIdOrThrow(Long id) {
        return robotRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Robot not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<RobotResponse> getRobotsByWarehouse(Long warehouseId) {
        return robotRepository.findByWarehouseId(warehouseId)
                .stream()
                .map(RobotResponse::from)
                .toList();
    }

    // 로봇 생성
    public RobotResponse createRobot(CreateRobotRequest dto) {
        Robot robot = Robot.builder()
                .name(dto.getName())
                .batteryPercentage(dto.getBatteryPercentage())
                .speed(dto.getSpeed())
                .position(Position.of(dto.getPosition()))
                .rotation(Quaternion.of(dto.getRotation()))
                .size(Size.of(dto.getSize()))
                .status(RobotStatus.IDLE)           // 기본값 설정
                .mode(RobotMode.NORMAL)             // 기본값 설정
                .deviceType(DeviceType.ROBOT)       // 기본값 설정
                .warehouseId(dto.getWarehouseId())  // FK 설정
                .build();

        Robot savedRobot = robotRepository.save(robot);

        return RobotResponse.from(savedRobot);
    }

    // 로봇 수정
    public RobotResponse updateRobot(Long id, UpdateRobotRequest dto) {
        Robot robot = getByIdOrThrow(id);

        try {
            robot.update(
                    dto.getName(),
                    dto.getBatteryPercentage(),
                    dto.getSpeed(),
                    dto.getPosition(),
                    dto.getRotation(),
                    dto.getSize(),
                    dto.getStatus(),
                    dto.getMode()
            );
        } catch (IllegalArgumentException e) {
            // RobotStatue, RobotMode 처럼 유효하지 않은 Enum 데이터를 전송하면 기본값을 설정해도 되지만,
            // 실시간으로 로봇의 상태를 확인하여 모니터링하는게 좋다고 판단.
            throw new RuntimeException("Invalid status or mode value: " + e.getMessage());
        }

        return RobotResponse.from(robot);
    }

    // 로봇 삭제
    public void deleteRobot(Long id) {
        robotRepository.delete(getByIdOrThrow(id));
    }

}
