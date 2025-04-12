package com.minseojo.smartwarehouse.agv;

import com.minseojo.smartwarehouse.task.TaskQueue;
import com.minseojo.smartwarehouse.task.entity.Task;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class AGVTaskQueue implements TaskQueue {

    private final Queue<Task> queue = new ConcurrentLinkedQueue<>();

    @Override
    public void enqueue(Task task) {
        queue.offer(task);
    }

    @Override
    public Task poll() {
        return queue.poll();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
