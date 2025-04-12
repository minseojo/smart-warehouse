package com.minseojo.smartwarehouse.simulator;

import com.minseojo.smartwarehouse.agv.AGVManager;
import com.minseojo.smartwarehouse.agv.AGVTaskQueue;
import com.minseojo.smartwarehouse.agv.entity.AGV;
import com.minseojo.smartwarehouse.agv.entity.AGVStatus;
import com.minseojo.smartwarehouse.task.entity.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AGVSimulator {

    private final AGVManager agvManager;
    private final AGVTaskQueue taskBuffer;

    @Scheduled(fixedRate = 1000)
    @Transactional
    public void updateAGVs() {
        for (AGV agv : agvManager.getAll()) {
            agv.processNextTask();
            log.info("AGV 위치 {}", agv.getPosition());
            if (agv.getStatus() == AGVStatus.IDLE && !taskBuffer.isEmpty()) {
                Task task = taskBuffer.poll();
                agv.assignTask(task);
                agv.updateStatus(AGVStatus.WORKING);
                log.info("✅ AGV {} 작업 할당: {}", agv.getName(), task.getDescription());
            }
        }
    }
}
