package com.minseojo.smartwarehouse.robot.domain;

import com.minseojo.smartwarehouse.device.DeviceType;
import com.minseojo.smartwarehouse.robot.battery.RobotBatteryStatus;
import com.minseojo.smartwarehouse.warehouse.Warehouse;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.Transient;

@Entity
@Getter
public class Robot {
    @Id
    private Long id;

    private double batteryPercentage; // 배터리 잔량, 0.0 ~ 100.0

    @Enumerated(EnumType.STRING)
    private RobotStatus status;

    @Enumerated(EnumType.STRING)
    private RobotMode mode;

    private DeviceType deviceType = DeviceType.ROBOT;

    private int positionX;
    private int positionY;
    private int positionZ;

    @ManyToOne
    private Warehouse warehouse;

    // 배터리 상태는 배터리 퍼센트에서 계산 가능한 파생 데이터
    // 상태를 enum으로 영속화하면 관리가 더 복잡
    // 다른 시스템(예: 발전기, 드론)과 공통 로직을 BatteryStatus로 추출해 재사용 가능
    @Transient
    public RobotBatteryStatus getBatteryStatus() {
        return RobotBatteryStatus.from(batteryPercentage);
    }

    public void updateBatteryPercentage(double batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public boolean isMoving() {
        return this.status == RobotStatus.MOVING;
    }

    public boolean isIdle() {
        return this.status == RobotStatus.IDLE;
    }

    public boolean isCharging() {
        return this.status == RobotStatus.CHARGING;
    }
    public boolean isWorking() {
        return this.status == RobotStatus.WORKING;
    }
    public boolean isError() {
        return this.status == RobotStatus.ERROR;
    }

    public boolean isNormalMode() {
        return this.mode == RobotMode.NORMAL;
    }

    public boolean isSavingMode() {
        return this.mode == RobotMode.SAVING;
    }

    public boolean isReturnToChargerMode() {
        return this.mode == RobotMode.RETURN_TO_CHARGER;
    }

}

