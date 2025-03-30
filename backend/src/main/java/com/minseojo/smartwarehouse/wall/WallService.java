package com.minseojo.smartwarehouse.wall;

import com.minseojo.smartwarehouse.wall.domain.Wall;
import com.minseojo.smartwarehouse.wall.dto.CreateWallRequest;
import com.minseojo.smartwarehouse.wall.dto.UpdateWallRequest;
import com.minseojo.smartwarehouse.wall.dto.WallResponse;
import com.minseojo.smartwarehouse.warehouse.WarehouseRepository;
import com.minseojo.smartwarehouse.warehouse.domain.Warehouse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WallService {

    private final WallRepository wallRepository;
    private final WarehouseRepository warehouseRepository;

    @Transactional(readOnly = true)
    public Wall getByIdOrThrow(Long id) {
        return wallRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Wall not found: " + id));
    }

    public WallResponse create(Long warehouseId, CreateWallRequest dto) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new EntityNotFoundException("Warehouse not found"));

        Wall wall = Wall.builder()
                .description(dto.getDescription())
                .position(dto.getPosition())
                .size(dto.getSize())
                .rotation(dto.getRotation())
                .warehouse(warehouse)
                .build();

        return WallResponse.from(wallRepository.save(wall));
    }

    public WallResponse update(Long id, UpdateWallRequest dto) {
        Wall wall = getByIdOrThrow(id);
        wall.update(dto.getDescription(), dto.getPosition(), dto.getSize(), dto.getRotation(), dto.getColor());
        return WallResponse.from(wall);
    }

    public void delete(Long id) {
        wallRepository.delete(getByIdOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<WallResponse> getAll() {
        return wallRepository.findAll()
                .stream()
                .map(WallResponse::from)
                .toList();
    }
}
