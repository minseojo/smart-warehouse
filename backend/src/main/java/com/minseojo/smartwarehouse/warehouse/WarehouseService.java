package com.minseojo.smartwarehouse.warehouse;

import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import com.minseojo.smartwarehouse.warehouse.domain.Warehouse;
import com.minseojo.smartwarehouse.warehouse.dto.CreateWarehouseRequest;
import com.minseojo.smartwarehouse.warehouse.dto.UpdateWarehouseRequest;
import com.minseojo.smartwarehouse.warehouse.dto.WarehouseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    @Transactional(readOnly = true)
    public Warehouse getByIdOrThrow(Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found: " + id));
    }

    @Transactional(readOnly = true)
    public WarehouseResponse getById(Long id) {
        Warehouse warehouse = getByIdOrThrow(id);
        return WarehouseResponse.from(warehouse);
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
                Position.of(dto.getPosition()),
                Quaternion.of(dto.getRotation()),
                Size.of(dto.getSize())
        );

        return WarehouseResponse.from(warehouse);
    }


    public void delete(Long id) {
        warehouseRepository.delete(getByIdOrThrow(id));
    }
}
