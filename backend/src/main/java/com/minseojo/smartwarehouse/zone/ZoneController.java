package com.minseojo.smartwarehouse.zone;

import com.minseojo.smartwarehouse.zone.dto.CreateZoneRequest;
import com.minseojo.smartwarehouse.zone.dto.UpdateZoneRequest;
import com.minseojo.smartwarehouse.zone.dto.ZoneResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;


    @PostMapping
    public ZoneResponse create(@RequestParam Long warehouseId, @RequestBody CreateZoneRequest requestDTO) {
        return zoneService.create(warehouseId, requestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestParam Long warehouseId, @RequestBody UpdateZoneRequest dto) {
        zoneService.update(id, warehouseId, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        zoneService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
