package com.minseojo.smartwarehouse.agv;

import com.minseojo.smartwarehouse.task.TaskDispatcher;
import com.minseojo.smartwarehouse.task.TaskQueue;
import com.minseojo.smartwarehouse.task.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InMemoryAGVTaskDispatcher implements TaskDispatcher {
    private final TaskQueue taskQueue;

    @Override
    public void dispatch(Task task) {
        taskQueue.enqueue(task);
    }
}
