package com.minseojo.smartwarehouse.agv;

import com.minseojo.smartwarehouse.agv.entity.AGV;
import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import com.minseojo.smartwarehouse.device.DeviceType;
import com.minseojo.smartwarehouse.agv.entity.AGVMode;
import com.minseojo.smartwarehouse.agv.entity.AGVStatus;
import com.minseojo.smartwarehouse.agv.dto.CreateAGVRequest;
import com.minseojo.smartwarehouse.agv.dto.AGVResponse;
import com.minseojo.smartwarehouse.agv.dto.UpdateAGVRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AGVService {

    private final AGVRepository AGVRepository;

    @Transactional(readOnly = true)
    public AGV getByIdOrThrow(Long id) {
        return AGVRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Robot not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<AGVResponse> getAGVsByWarehouse(Long warehouseId) {
        return AGVRepository.findByWarehouseId(warehouseId)
                .stream()
                .map(AGVResponse::from)
                .toList();
    }

    // 로봇 생성
    public AGVResponse createAGV(CreateAGVRequest dto) {
        AGV agv = AGV.builder()
                .name(dto.getName())
                .batteryPercentage(dto.getBatteryPercentage())
                .speed(dto.getSpeed())
                .position(Position.of(dto.getPosition()))
                .rotation(Quaternion.of(dto.getRotation()))
                .size(Size.of(dto.getSize()))
                .status(AGVStatus.IDLE)           // 기본값 설정
                .mode(AGVMode.NORMAL)             // 기본값 설정
                .deviceType(DeviceType.AGV)       // 기본값 설정
                .warehouseId(dto.getWarehouseId())  // FK 설정
                .build();

        AGV savedAGV = AGVRepository.save(agv);

        return AGVResponse.from(savedAGV);
    }

    // 로봇 수정
    public AGVResponse updateAGV(Long id, UpdateAGVRequest dto) {
        AGV AGV = getByIdOrThrow(id);

        try {
            AGV.update(
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

        return AGVResponse.from(AGV);
    }

    // 로봇 삭제
    public void deleteAGV(Long id) {
        AGVRepository.delete(getByIdOrThrow(id));
    }

}
