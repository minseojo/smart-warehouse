package com.minseojo.smartwarehouse.simulator;

import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.task.TaskDispatcher;
import com.minseojo.smartwarehouse.task.entity.Task;
import com.minseojo.smartwarehouse.task.entity.TaskType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TaskGenerator {

    private final TaskDispatcher dispatcher;

    private long counter = 0;

    @Scheduled(fixedRate = 10000)
    public void generateTask() {
        Task task = Task.builder()
                .description("자동 생성 작업 #" + (++counter))
                .type(TaskType.INBOUND)
                .source(new Position(300, 1, 0))
                .destination(new Position(500, 1, 0))
                .build();

        dispatcher.dispatch(task); // 추상화된 dispatcher 사용
        log.info("✅ 작업 생성됨: {}", task.getDescription());
    }
}
