package com.minseojo.smartwarehouse;

import com.minseojo.smartwarehouse.common.vo.Color;
import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
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

    @Override
    public void run(String... args) throws Exception {
        int START_X = 0;
        int START_Y = 0;

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
                .position(new Position(START_X, START_Y, 0)) // 좌표 예시
                .size(new Size(400, 200, 10))   // 크기 (가로, 세로, 높이)
                .rotation(Quaternion.identity()) // 회전 (기본값)
                .build();
        warehouseRepository.save(warehouse);

        // 창고에 Zone 저장
        warehouseRepository.findById(warehouse.getId());

        zoneRepository.save(Zone.builder()
                .name("입고 구역")
                .description("입고를 위한 구역")
                .position(new Position(START_X - 500, START_Y, 0))
                .size(new Size(200, ZONE_HEIGHT, 1))
                .rotation(Quaternion.identity())
                .type(ZoneType.INBOUND)
                .color(Color.BLUE)
                .warehouse(warehouse)
                .build());

        zoneRepository.save(Zone.builder()
                .name("입고 보관 구역")
                .description("입고된 물품을 보관하는 구역")
                .position(new Position(START_X - 250, START_Y, 0))
                .size(new Size(300, ZONE_HEIGHT, 1))
                .rotation(Quaternion.identity())
                .type(ZoneType.INBOUND_STORAGE)
                .color(Color.GREEN)
                .warehouse(warehouse)
                .build());

        zoneRepository.save(Zone.builder()
                .name("작업 구역")
                .description("작업을 위한 구역")
                .position(new Position(START_X +-0, START_Y + 0, 0))
                .size(new Size(200, ZONE_HEIGHT, 1))
                .rotation(Quaternion.identity())
                .type(ZoneType.OUTBOUND)
                .color(Color.YELLOW)
                .warehouse(warehouse)
                .build());

        zoneRepository.save(Zone.builder()
                .name("출고 보관 구역")
                .description("출고 준비가 된 물품을 보관하는 구역")
                .position(new Position(START_X + 250, START_Y, 0))
                .size(new Size(300, ZONE_HEIGHT, 1))
                .rotation(Quaternion.identity())
                .type(ZoneType.OUTBOUND_STORAGE)
                .color(Color.ORANGE)
                .warehouse(warehouse)
                .build());

        zoneRepository.save(Zone.builder()
                .name("출고 구역")
                .description("출고를 위한 구역")
                .position(new Position(START_X + 500, START_Y, 0))
                .size(new Size(200, ZONE_HEIGHT, 1))
                .rotation(Quaternion.identity())
                .type(ZoneType.OUTBOUND)
                .color(Color.RED)
                .warehouse(warehouse)
                .build());

        // 좌벽
        wallRepository.save(Wall.builder()
                .description("Left Wall")
                .position(new Position(START_X + -WALL_TOP_BOTTOM_WIDTH / 2, START_Y + WALL_HEIGHT / 2, 0))
                .size(new Size(WALL_THICKNESS, WALL_HEIGHT, WALL_SIDE_WIDTH + 20))
                .rotation(Quaternion.identity())
                .warehouse(warehouse)
                .build());

        // 우벽
        wallRepository.save(Wall.builder()
                .description("Right Wall")
                .position(new Position(START_X + WALL_TOP_BOTTOM_WIDTH / 2, START_Y + WALL_HEIGHT / 2, 0))
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
                .description("Floor")
                .position(new Position(START_X, START_Y + -1, 0))
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

    }
}
