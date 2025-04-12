package com.minseojo.smartwarehouse;

import com.minseojo.smartwarehouse.agv.AGVManager;
import com.minseojo.smartwarehouse.agv.AGVRepository;
import com.minseojo.smartwarehouse.agv.entity.AGV;
import com.minseojo.smartwarehouse.agv.entity.AGVMode;
import com.minseojo.smartwarehouse.agv.entity.AGVStatus;
import com.minseojo.smartwarehouse.common.vo.Color;
import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import com.minseojo.smartwarehouse.device.DeviceType;
import com.minseojo.smartwarehouse.rack.RackRepository;
import com.minseojo.smartwarehouse.rack.entity.Rack;
import com.minseojo.smartwarehouse.wall.WallRepository;
import com.minseojo.smartwarehouse.wall.entity.Wall;
import com.minseojo.smartwarehouse.warehouse.WarehouseRepository;
import com.minseojo.smartwarehouse.warehouse.entity.Warehouse;
import com.minseojo.smartwarehouse.zone.ZoneRepository;
import com.minseojo.smartwarehouse.zone.entity.Zone;
import com.minseojo.smartwarehouse.zone.entity.ZoneType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final WarehouseRepository warehouseRepository;
    private final ZoneRepository zoneRepository;
    private final WallRepository wallRepository;
    private final RackRepository rackRepository;

    private final AGVRepository agvRepository;
    private final AGVManager agvManager;

    @Override
    public void run(String... args) throws Exception {
        int START_X = 0;
        int START_Y = 0;
        int START_Z = 0;

        int WALL_SIDE_WIDTH = 700;            // 좌우 벽 길이 (Z 방향)
        int WALL_HEIGHT = 100;                // 벽 높이 (Y 방향)
        int WALL_TOP_BOTTOM_WIDTH = 1200;     // 상하 벽 길이 (X 방향)
        int WALL_THICKNESS = 20;              // 벽 두께 (X 또는 Z 방향)

        int ZONE_HEIGHT = 700;

        int FLOOR_THICKNESS = 2;              // 바닥 두께

        // 창고 저장
        Warehouse warehouse = Warehouse.builder()
                .name("민서의 스마트 물류 창고")
                .description("민서의 메인 스마트 물류 창고")
                .position(new Position(START_X, START_Y, START_Z)) // 좌표 예시
                .size(new Size(400, 200, 10))   // 크기 (가로, 세로, 높이)
                .rotation(Quaternion.identity()) // 회전 (기본값)
                .build();
        warehouseRepository.save(warehouse);

        // 창고에 Zone 저장
        warehouseRepository.findById(warehouse.getId());

        zoneRepository.save(Zone.builder()
                .name("입고 구역")
                .description("입고를 위한 구역")
                .position(new Position(START_X - 500, START_Y, START_Z))
                .size(new Size(200, ZONE_HEIGHT, 1))
                .rotation(Quaternion.identity())
                .type(ZoneType.INBOUND)
                .color(Color.BLUE)
                .warehouse(warehouse)
                .build());

        zoneRepository.save(Zone.builder()
                .name("입고 보관 구역")
                .description("입고된 물품을 보관하는 구역")
                .position(new Position(START_X - 250, START_Y, START_Z))
                .size(new Size(300, ZONE_HEIGHT, 1))
                .rotation(Quaternion.identity())
                .type(ZoneType.INBOUND_STORAGE)
                .color(Color.GREEN)
                .warehouse(warehouse)
                .build());

        zoneRepository.save(Zone.builder()
                .name("작업 구역")
                .description("작업을 위한 구역")
                .position(new Position(START_X, START_Y, START_Z))
                .size(new Size(200, ZONE_HEIGHT, 1))
                .rotation(Quaternion.identity())
                .type(ZoneType.OUTBOUND)
                .color(Color.YELLOW)
                .warehouse(warehouse)
                .build());

        zoneRepository.save(Zone.builder()
                .name("출고 보관 구역")
                .description("출고 준비가 된 물품을 보관하는 구역")
                .position(new Position(START_X + 250, START_Y, START_Z))
                .size(new Size(300, ZONE_HEIGHT, 1))
                .rotation(Quaternion.identity())
                .type(ZoneType.OUTBOUND_STORAGE)
                .color(Color.ORANGE)
                .warehouse(warehouse)
                .build());

        zoneRepository.save(Zone.builder()
                .name("출고 구역")
                .description("출고를 위한 구역")
                .position(new Position(START_X + 500, START_Y, START_Z))
                .size(new Size(200, ZONE_HEIGHT, 1))
                .rotation(Quaternion.identity())
                .type(ZoneType.OUTBOUND)
                .color(Color.RED)
                .warehouse(warehouse)
                .build());

        // 좌벽
        wallRepository.save(Wall.builder()
                .description("Left Wall")
                .position(new Position(START_X + -WALL_TOP_BOTTOM_WIDTH / 2, START_Y + WALL_HEIGHT / 2, START_Z))
                .size(new Size(WALL_THICKNESS, WALL_HEIGHT, WALL_SIDE_WIDTH + 20))
                .rotation(Quaternion.identity())
                .warehouse(warehouse)
                .build());

        // 우벽
        wallRepository.save(Wall.builder()
                .description("Right Wall")
                .position(new Position(START_X + WALL_TOP_BOTTOM_WIDTH / 2, START_Y + WALL_HEIGHT / 2, START_Z))
                .size(new Size(WALL_THICKNESS, WALL_HEIGHT, WALL_SIDE_WIDTH + 20))
                .rotation(Quaternion.identity())
                .warehouse(warehouse)
                .build());

        // 상벽
        wallRepository.save(Wall.builder()
                .description("Top Wall")
                .position(new Position(START_X + 0, START_Y + WALL_HEIGHT / 2, -WALL_SIDE_WIDTH / 2))
                .size(new Size(WALL_TOP_BOTTOM_WIDTH, WALL_HEIGHT, WALL_THICKNESS))
                .rotation(Quaternion.identity())
                .warehouse(warehouse)
                .build());

        // 하벽
        wallRepository.save(Wall.builder()
                .description("Bottom Wall")
                .position(new Position(START_X + 0, START_Y + WALL_HEIGHT / 2, WALL_SIDE_WIDTH / 2))
                .size(new Size(WALL_TOP_BOTTOM_WIDTH, WALL_HEIGHT, WALL_THICKNESS))
                .rotation(Quaternion.identity())
                .warehouse(warehouse)
                .build());

        // 바닥
        wallRepository.save(Wall.builder()
                .description("")
                .position(new Position(START_X, START_Y + -1, START_Z))
                .size(new Size(WALL_TOP_BOTTOM_WIDTH, FLOOR_THICKNESS, WALL_SIDE_WIDTH))
                .rotation(Quaternion.identity())
                        .color(Color.FLOOR_LIGHT)
                .warehouse(warehouse)
                .build());


//        wallRepository.save(Wall.builder()
//                .description("Middle")
//                .position(new Position(START_X - 400, START_Y + 0, 0))
//                .size(new Size(WALL_TOP_BOTTOM_WIDTH / 3, WALL_HEIGHT, 10))
//                .rotation(Quaternion.identity())
//                .color(Color.INSIDE_WALL_GRAY)
//                .warehouse(warehouse)
//                .build());

        // Zone 참조 가져오기
        Zone inboundStorageZone = zoneRepository.findByName("입고 보관 구역")
                .orElseThrow(() -> new IllegalStateException("입고 보관 구역을 찾을 수 없습니다."));
        Zone outboundStorageZone = zoneRepository.findByName("출고 보관 구역")
                .orElseThrow(() -> new IllegalStateException("출고 보관 구역을 찾을 수 없습니다."));

        int BOX_WIDTH = 50;
        // 입고 보관 구역에 랙 추가
        int inboundRackIndex = 0;
        for (int i = 0; i < 11; i++) {
            if ((i + 1) % 3 == 0) continue;
            for (int j = 0; j < 2; j++) {
                int x = j * 150;
                int z = i * 60;
                rackRepository.save(Rack.builder()
                        .name("INBOUND-RACK-" + inboundRackIndex)
                        .description("입고 보관용 랙 " + inboundRackIndex)
                        .position(new Position(START_X - 325 + x, START_Y, START_Z - 300 + z))
                        .size(new Size(140, 50, 50))
                        .rotation(Quaternion.identity())
                        .zone(inboundStorageZone)
                        .warehouse(warehouse)
                        .build());
                inboundRackIndex++;
            }
        }

        // 출고 보관 구역에 랙 추가
        int outboundRackIndex = 0;
        for (int i = 0; i < 11; i++) {
            if ((i + 1) % 3 == 0) continue;
            for (int j = 0; j < 2; j++) {
                int x = j * 150;
                int z = i * 60;
                rackRepository.save(Rack.builder()
                        .name("OUTBOUND-RACK-" + outboundRackIndex)
                        .description("출고 보관용 랙 " + outboundRackIndex)
                        .position(new Position(START_X + 175 + x, START_Y, START_Z - 300 + z))
                        .size(new Size(140, 50, 50))
                        .rotation(Quaternion.identity())
                        .zone(outboundStorageZone)
                        .warehouse(warehouse)
                        .build());
                outboundRackIndex++;
            }
        }

        // AGV 3대 추가 (입고구역 근처)
        agvRepository.save(AGV.builder()
                .name("AGV-1")
                .position(new Position(START_X - 550, START_Y, START_Z - 100))
                .rotation(Quaternion.identity())
                .size(new Size(50, 50, 50))
                .speed(10)
                .batteryPercentage(100.0)
                .status(AGVStatus.IDLE)
                .mode(AGVMode.NORMAL)
                .deviceType(DeviceType.AGV)
                .warehouseId(warehouse.getId())
                .build());

        agvRepository.save(AGV.builder()
                .name("AGV-2")
                .position(new Position(START_X - 550, START_Y, START_Z))
                .rotation(Quaternion.identity())
                .size(new Size(50, 50, 50))
                .speed(10)
                .batteryPercentage(100.0)
                .status(AGVStatus.IDLE)
                .mode(AGVMode.NORMAL)
                .deviceType(DeviceType.AGV)
                .warehouseId(warehouse.getId())
                .build());

        agvRepository.save(AGV.builder()
                .name("AGV-3")
                .position(new Position(START_X - 550, START_Y, START_Z + 100))
                .rotation(Quaternion.identity())
                .size(new Size(50, 50, 50))
                .speed(10)
                .batteryPercentage(100.0)
                .status(AGVStatus.IDLE)
                .mode(AGVMode.NORMAL)
                .deviceType(DeviceType.AGV)
                .warehouseId(warehouse.getId())
                .build());

        agvRepository.findAll().forEach(agvManager::register);

    }
}
