package com.minseojo.smartwarehouse.robot.domain;

import com.minseojo.smartwarehouse.common.entity.BaseTimeEntity;
import com.minseojo.smartwarehouse.common.vo.OBB;
import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import com.minseojo.smartwarehouse.device.DeviceType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@Getter
public class Robot extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double batteryPercentage; // 배터리 잔량, 0.0 ~ 100.0

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "position_x")),
            @AttributeOverride(name = "y", column = @Column(name = "position_y")),
            @AttributeOverride(name = "z", column = @Column(name = "position_z"))
    })
    private Position position; // 위치 (절대적으로 어디에 위치함)

    @Embedded
    private Size size; // 부피

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "rotation_x")),
            @AttributeOverride(name = "y", column = @Column(name = "rotation_y")),
            @AttributeOverride(name = "z", column = @Column(name = "rotation_z")),
            @AttributeOverride(name = "w", column = @Column(name = "rotation_w"))
    })
    private Quaternion rotation; // 회전 상태

    private double speed;

    @Enumerated(EnumType.STRING)
    private RobotStatus status;

    @Enumerated(EnumType.STRING)
    private RobotMode mode;

    @Enumerated(EnumType.STRING)
    private DeviceType deviceType = DeviceType.ROBOT;

    private Long warehouseId;  // 약한 연관 (FK만)

    @Transient // DB 저장 X
    private OBB obb;

    // 생성 시 자동 OBB 설정
    @PostLoad
    @PostPersist
    @PostUpdate
    private void initOBB() {
        this.obb = OBB.from(position, rotation, size);
    }

    // 위치 이동
    public void move(Position newPosition) {
        this.position = newPosition;
        updateOBB();
    }

    // 회전 변경
    public void rotate(Quaternion newRotation) {
        this.rotation = newRotation;
        updateOBB();
    }

    // 크기 변경
    public void resize(Size newSize) {
        this.size = newSize;
        updateOBB();
    }

    // 내부 전용 OBB 갱신
    private void updateOBB() {
        this.obb = OBB.from(position, rotation, size);
    }

    public void update(String name, double battery, double speed,
                       Position pos, Quaternion rot, Size size,
                       RobotStatus status, RobotMode mode) {
        this.name = name;
        this.batteryPercentage = battery;
        this.speed = speed;
        this.position = pos;
        this.rotation = rot;
        this.size = size;
        this.status = status;
        this.mode = mode;
    }

}

