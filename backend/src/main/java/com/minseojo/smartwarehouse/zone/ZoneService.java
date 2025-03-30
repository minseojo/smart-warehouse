package com.minseojo.smartwarehouse.zone;

import com.minseojo.smartwarehouse.zone.domain.Zone;
import com.minseojo.smartwarehouse.zone.dto.CreateZoneRequest;
import com.minseojo.smartwarehouse.zone.dto.UpdateZoneRequest;
import com.minseojo.smartwarehouse.zone.dto.ZoneResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ZoneService {

    private final ZoneRepository zoneRepository;

    @Transactional(readOnly = true)
    public Zone getByIdOrThrow(Long id) {
        return zoneRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Zone not found: " + id));
    }

    @Transactional(readOnly = true)
    public ZoneResponse getById(Long id) {
        return ZoneResponse.from(getByIdOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<ZoneResponse> getAll() {
        return zoneRepository.findAll().stream()
                .map(ZoneResponse::from)
                .toList();
    }

    public ZoneResponse create(CreateZoneRequest dto) {
        Zone zone = Zone.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .position(dto.getPosition())
                .size(dto.getSize())
                .rotation(dto.getRotation())
                .type(dto.getType())
                .warehouseId(dto.getWarehouseId())
                .build();

        Zone saved = zoneRepository.save(zone);
        return ZoneResponse.from(saved);
    }

    public ZoneResponse update(Long id, UpdateZoneRequest dto) {
        Zone zone = getByIdOrThrow(id);
        zone = Zone.builder()
                .id(zone.getId()) // 기존 ID 유지
                .name(dto.getName())
                .description(dto.getDescription())
                .position(dto.getPosition())
                .size(dto.getSize())
                .rotation(dto.getRotation())
                .type(dto.getType())
                .warehouseId(zone.getWarehouseId()) // FK 유지
                .build();

        return ZoneResponse.from(zoneRepository.save(zone));
    }

    public void delete(Long id) {
        zoneRepository.delete(getByIdOrThrow(id));
    }
}
