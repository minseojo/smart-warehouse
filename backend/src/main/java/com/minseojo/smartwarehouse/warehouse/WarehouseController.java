package com.minseojo.smartwarehouse.warehouse;

import com.minseojo.smartwarehouse.warehouse.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    public WarehouseResponse create(@RequestBody CreateWarehouseRequest requestDTO) {
        return warehouseService.create(requestDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseStructureResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.getById(id));
    }

    @GetMapping
    public List<WarehouseResponse> getAll() {
        return warehouseService.getAll();
    }

    @PutMapping("/{id}")
    public WarehouseResponse update(@PathVariable Long id, @RequestBody UpdateWarehouseRequest requestDTO) {
        return warehouseService.update(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        warehouseService.delete(id);
    }

    @GetMapping("/{id}/structure")
    public ResponseEntity<WarehouseStructureResponse> getStructure(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.getStructure(id));
    }

    @GetMapping("/{id}/state")
    public ResponseEntity<WarehouseStateResponse> getState(@PathVariable Long id) {
        return ResponseEntity.ok(warehouseService.getState(id));
    }

}
