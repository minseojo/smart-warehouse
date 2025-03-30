package com.minseojo.smartwarehouse.wall;

import com.minseojo.smartwarehouse.wall.dto.CreateWallRequest;
import com.minseojo.smartwarehouse.wall.dto.UpdateWallRequest;
import com.minseojo.smartwarehouse.wall.dto.WallResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/walls")
public class WallController {

    private final WallService wallService;

    @PostMapping
    public WallResponse create(@RequestParam Long warehouseId, @RequestBody CreateWallRequest requestDTO) {
        return wallService.create(warehouseId, requestDTO);
    }

    @PutMapping("/{id}")
    public WallResponse update(@PathVariable Long id, @RequestBody UpdateWallRequest requestDTO) {
        return wallService.update(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        wallService.delete(id);
    }

}
