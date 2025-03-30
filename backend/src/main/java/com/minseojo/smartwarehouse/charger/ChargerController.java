package com.minseojo.smartwarehouse.charger;

import com.minseojo.smartwarehouse.charger.dto.ChargerResponse;
import com.minseojo.smartwarehouse.charger.dto.CreateChargerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chargers")
@RequiredArgsConstructor
public class ChargerController {

    private final ChargerService chargerService;

    @PostMapping
    public ChargerResponse create(@RequestBody CreateChargerRequest requestDTO) {
        return chargerService.create(requestDTO);
    }
}
