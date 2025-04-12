package com.minseojo.smartwarehouse.warehouse;

import com.minseojo.smartwarehouse.agv.AGVService;
import com.minseojo.smartwarehouse.agv.dto.AGVResponse;
import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import com.minseojo.smartwarehouse.rack.dto.RackResponse;
import com.minseojo.smartwarehouse.wall.dto.WallResponse;
import com.minseojo.smartwarehouse.warehouse.dto.*;
import com.minseojo.smartwarehouse.warehouse.entity.Warehouse;
import com.minseojo.smartwarehouse.zone.dto.ZoneResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final AGVService agvService;

    @Transactional(readOnly = true)
    public Warehouse getByIdOrThrow(Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found: " + id));
    }

    @Transactional(readOnly = true)
    public WarehouseStructureResponse getById(Long id) {
        Warehouse warehouse = getByIdOrThrow(id);
        return WarehouseStructureResponse.from(warehouse);
    }

    @Transactional(readOnly = true)
    public List<WarehouseResponse> getAll() {
        return warehouseRepository.findAll()
                .stream()
                .map(WarehouseResponse::from)
                .toList();
    }

    public WarehouseResponse create(CreateWarehouseRequest dto) {
        Warehouse warehouse = Warehouse.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .position(Position.of(dto.getPosition()))
                .rotation(Quaternion.of(dto.getRotation()))
                .size(Size.of(dto.getSize()))
                .build();

        Warehouse savedWarehouse = warehouseRepository.save(warehouse);

        return WarehouseResponse.from(savedWarehouse);
    }

    public WarehouseResponse update(Long id, UpdateWarehouseRequest dto) {
        Warehouse warehouse = getByIdOrThrow(id);

        warehouse.update(
                dto.getName(),
                dto.getDescription(),
                dto.getPosition(),
                dto.getRotation(),
                dto.getSize()
        );

        return WarehouseResponse.from(warehouse);
    }

    public WarehouseStructureResponse getStructure(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("창고 없음"));

        return new WarehouseStructureResponse(
                warehouse.getName(),
                warehouse.getDescription(),
                warehouse.getPosition(),
                warehouse.getSize(),
                warehouse.getRotation(),
                warehouse.getWalls().stream().map(WallResponse::from).toList(),
                warehouse.getZones().stream().map(ZoneResponse::from).toList()
        );
    }

    public WarehouseStateResponse getState(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("창고 없음"));

        return new WarehouseStateResponse(
                warehouse.getRacks().stream().map(RackResponse::from).toList(),
                agvService.getAGVsByWarehouseFromAGVManger(id)
        );
    }



    public void delete(Long id) {
        warehouseRepository.delete(getByIdOrThrow(id));
    }
}
