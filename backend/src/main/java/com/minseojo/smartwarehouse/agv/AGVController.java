package com.minseojo.smartwarehouse.agv;

import com.minseojo.smartwarehouse.agv.dto.CreateAGVRequest;
import com.minseojo.smartwarehouse.agv.dto.AGVResponse;
import com.minseojo.smartwarehouse.agv.dto.UpdateAGVRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/robots")
@RequiredArgsConstructor
@Slf4j
public class AGVController {

    private final AGVService AGVService;

    @PostMapping
    public AGVResponse create(@RequestBody CreateAGVRequest requestDTO) {
        return AGVService.createAGV(requestDTO);
    }

    @PutMapping("/{id}")
    public AGVResponse update(@PathVariable Long id, @RequestBody UpdateAGVRequest requestDTO) {
        return AGVService.updateAGV(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        AGVService.deleteAGV(id);
        return ResponseEntity.noContent().build();  // 삭제 되면, HTTP 204 No Content 반환
    }

    @GetMapping
    public List<AGVResponse> getAGVsByWarehouse(@RequestParam Long warehouseId) {
        return AGVService.getAGVsByWarehouse(warehouseId);
    }

}
