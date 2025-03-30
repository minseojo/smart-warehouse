package com.minseojo.smartwarehouse.robot;

import com.minseojo.smartwarehouse.robot.dto.CreateRobotRequest;
import com.minseojo.smartwarehouse.robot.dto.RobotResponse;
import com.minseojo.smartwarehouse.robot.dto.UpdateRobotRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/robots")
@RequiredArgsConstructor
@Slf4j
public class RobotController {

    private final RobotService robotService;

    @PostMapping
    public RobotResponse create(@RequestBody CreateRobotRequest requestDTO) {
        return robotService.createRobot(requestDTO);
    }

    @PutMapping("/{id}")
    public RobotResponse update(@PathVariable Long id, @RequestBody UpdateRobotRequest requestDTO) {
        return robotService.updateRobot(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        robotService.deleteRobot(id);
        return ResponseEntity.noContent().build();  // 삭제 되면, HTTP 204 No Content 반환
    }

    @GetMapping
    public List<RobotResponse> getRobotsByWarehouse(@RequestParam Long warehouseId) {
        return robotService.getRobotsByWarehouse(warehouseId);
    }

}
