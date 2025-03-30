package com.minseojo.smartwarehouse.charger;

import com.minseojo.smartwarehouse.charger.domain.Charger;
import com.minseojo.smartwarehouse.charger.dto.ChargerResponse;
import com.minseojo.smartwarehouse.charger.dto.CreateChargerRequest;
import com.minseojo.smartwarehouse.zone.domain.Zone;
import com.minseojo.smartwarehouse.zone.ZoneRepository;
import com.minseojo.smartwarehouse.zone.domain.ZoneType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChargerService {

    private final ChargerRepository chargerRepository;
    private final ZoneRepository zoneRepository;

    public ChargerResponse create(CreateChargerRequest dto) {
        Zone zone = zoneRepository.findById(dto.getZoneId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Zone"));

        if (zone.getType() != ZoneType.CHARGING) {
            throw new IllegalArgumentException("Charger는 CHARGING Zone에만 배치할 수 있습니다.");
        }

        Charger charger = Charger.builder()
                .name(dto.getName())
                .position(dto.getPosition())
                .size(dto.getSize())
                .rotation(dto.getRotation())
                .zone(zone)
                .build();

        return ChargerResponse.from(chargerRepository.save(charger));
    }
}
