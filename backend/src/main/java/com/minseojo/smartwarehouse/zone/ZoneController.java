package com.minseojo.smartwarehouse.zone;

import com.minseojo.smartwarehouse.zone.dto.CreateZoneRequest;
import com.minseojo.smartwarehouse.zone.dto.UpdateZoneRequest;
import com.minseojo.smartwarehouse.zone.dto.ZoneResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;

    @PostMapping
    public ZoneResponse create(@RequestBody CreateZoneRequest dto) {
        return zoneService.create(dto);
    }

    @GetMapping("/{id}")
    public ZoneResponse getById(@PathVariable Long id) {
        return zoneService.getById(id);
    }

    @GetMapping
    public List<ZoneResponse> getAll() {
        return zoneService.getAll();
    }

    @PutMapping("/{id}")
    public ZoneResponse update(@PathVariable Long id, @RequestBody UpdateZoneRequest dto) {
        return zoneService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        zoneService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
