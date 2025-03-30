package com.minseojo.smartwarehouse.zone;

import com.minseojo.smartwarehouse.warehouse.WarehouseRepository;
import com.minseojo.smartwarehouse.warehouse.domain.Warehouse;
import com.minseojo.smartwarehouse.zone.domain.Zone;
import com.minseojo.smartwarehouse.zone.dto.CreateZoneRequest;
import com.minseojo.smartwarehouse.zone.dto.UpdateZoneRequest;
import com.minseojo.smartwarehouse.zone.dto.ZoneResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ZoneService {

    private final ZoneRepository zoneRepository;
    private final WarehouseRepository warehouseRepository;

    @Transactional(readOnly = true)
    public Zone getByIdOrThrow(Long id) {
        return zoneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Zone not found: " + id));
    }

    public ZoneResponse create(Long warehouseId, CreateZoneRequest dto) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new EntityNotFoundException("Warehouse not found"));

        Zone zone = Zone.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .position(dto.getPosition())
                .size(dto.getSize())
                .rotation(dto.getRotation())
                .type(dto.getType())
                .warehouse(warehouse)
                .build();

        Zone saved = zoneRepository.save(zone);
        return ZoneResponse.from(saved);
    }

    public ZoneResponse update(Long id, Long warehouseId, UpdateZoneRequest dto) {
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new EntityNotFoundException("Warehouse not found"));

        Zone zone = getByIdOrThrow(id);

        zone.update(
                dto.getName(),
                dto.getDescription(),
                dto.getPosition(),
                dto.getSize(),
                dto.getRotation(),
                dto.getType(),
                dto.getColor(),
                warehouse
        );

        return ZoneResponse.from(zoneRepository.save(zone));
    }

    public void delete(Long id) {
        zoneRepository.delete(getByIdOrThrow(id));
    }

}
