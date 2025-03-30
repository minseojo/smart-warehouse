package com.minseojo.smartwarehouse.rack;

import com.minseojo.smartwarehouse.rack.dto.CreateRackRequest;
import com.minseojo.smartwarehouse.rack.dto.RackResponse;
import com.minseojo.smartwarehouse.rack.dto.UpdateRackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/racks")
@RequiredArgsConstructor
public class RackController {

    private final RackService rackService;

    @PostMapping
    public RackResponse create(@RequestBody CreateRackRequest requestDTO) {
        return rackService.create(requestDTO);
    }

    @GetMapping("/{id}")
    public RackResponse get(@PathVariable Long id) {
        return rackService.get(id);
    }

    @GetMapping
    public List<RackResponse> getAll() {
        return rackService.getAll();
    }

    @PutMapping("/{id}")
    public RackResponse update(@PathVariable Long id, @RequestBody UpdateRackRequest requestDTO) {
        return rackService.update(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rackService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
