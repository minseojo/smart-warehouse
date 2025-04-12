package com.minseojo.smartwarehouse.rack;

import com.minseojo.smartwarehouse.rack.entity.Rack;
import com.minseojo.smartwarehouse.rack.dto.CreateRackRequest;
import com.minseojo.smartwarehouse.rack.dto.RackResponse;
import com.minseojo.smartwarehouse.rack.dto.UpdateRackRequest;
import com.minseojo.smartwarehouse.zone.ZoneRepository;
import com.minseojo.smartwarehouse.zone.entity.Zone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RackService {

    private final RackRepository rackRepository;
    private final ZoneRepository zoneRepository;

    @Transactional(readOnly = true)
    public Rack getByIdOrThrow(Long id) {
        return rackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rack not found: " + id));
    }

    @Transactional(readOnly = true)
    public RackResponse get(Long id) {
        return RackResponse.from(getByIdOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<RackResponse> getAll() {
        return rackRepository.findAll().stream()
                .map(RackResponse::from)
                .toList();
    }

    public RackResponse create(CreateRackRequest dto) {
        Zone zone = zoneRepository.findById(dto.getZoneId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Zone"));

        Rack rack = Rack.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .position(dto.getPosition())
                .size(dto.getSize())
                .rotation(dto.getRotation())
                .zone(zone)
                .build();

        return RackResponse.from(rackRepository.save(rack));
    }

    public RackResponse update(Long id, UpdateRackRequest dto) {
        Rack rack = getByIdOrThrow(id);

        rack.update(
                dto.getName(),
                dto.getDescription(),
                dto.getPosition(),
                dto.getSize(),
                dto.getRotation(),
                rack.getZone()
        );

        return RackResponse.from(rackRepository.save(rack));
    }

    public void delete(Long id) {
        rackRepository.delete(getByIdOrThrow(id));
    }
}
