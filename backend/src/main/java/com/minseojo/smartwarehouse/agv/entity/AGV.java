package com.minseojo.smartwarehouse.agv.entity;

import com.minseojo.smartwarehouse.common.entity.BaseTimeEntity;
import com.minseojo.smartwarehouse.common.entity.RobotInterface;
import com.minseojo.smartwarehouse.common.vo.OBB;
import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import com.minseojo.smartwarehouse.device.DeviceType;
import com.minseojo.smartwarehouse.task.entity.Task;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@Getter
@Slf4j
public class AGV extends BaseTimeEntity implements RobotInterface {

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
    private AGVStatus status;

    @Enumerated(EnumType.STRING)
    private AGVMode mode;

    @Enumerated(EnumType.STRING)
    private DeviceType deviceType = DeviceType.AGV;

    private Long warehouseId;  // 약한 연관 (FK만)

    @Transient // DB 저장 X
    private OBB obb;

    @Transient
    private Queue<Task> taskQueue = new LinkedList<>();

    public void assignTask(Task task) {
        this.taskQueue.add(task);
        this.status = AGVStatus.WORKING;
    }

    public void processNextTask() {
        if (taskQueue.isEmpty()) {
            this.status = AGVStatus.IDLE;
            return;
        }

        Task task = taskQueue.peek();
        
        // 현재 위치와 목적지 위치의 거리 계산
        double distance = position.distanceTo(task.getDestination());
        log.info("distance: {}", distance);
        // 목적지에 도달하지 않았으면 이동
        if (distance > 0.1) { // 0.1 단위 이상 차이나면 이동
            // 목적지 방향으로 이동
            double dx = task.getDestination().getX() - position.getX();
            double dy = task.getDestination().getY() - position.getY();
            double dz = task.getDestination().getZ() - position.getZ();
            
            // 정규화된 방향 벡터 계산
            double length = Math.sqrt(dx * dx + dy * dy + dz * dz);
            dx /= length;
            dy /= length;
            dz /= length;

            // 속도에 따라 이동
            Position newPosition = new Position(
                position.getX() + dx * speed,
                position.getY() + dy * speed,
                position.getZ() + dz * speed
            );
            
            this.move(newPosition);
            this.status = AGVStatus.MOVING;
            return;
        }

        // 도착했으면 작업 완료
        taskQueue.poll();
        if (taskQueue.isEmpty()) {
            this.status = AGVStatus.IDLE;
        }

    }

    // 위치 이동
    public void move(Position newPosition) {
        this.position = newPosition;
        updateOBB();
    }

    @Override
    public double getBatteryPercentage() {
        return batteryPercentage;
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
                       AGVStatus status, AGVMode mode) {
        this.name = name;
        this.batteryPercentage = battery;
        this.speed = speed;
        this.position = pos;
        this.rotation = rot;
        this.size = size;
        this.status = status;
        this.mode = mode;
    }

    public void updateStatus(AGVStatus status) {
        this.status = status;
    }

}

