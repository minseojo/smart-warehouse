package com.minseojo.smartwarehouse.task;

import com.minseojo.smartwarehouse.task.entity.Task;

public interface TaskQueue {
    void enqueue(Task task);
    Task poll();
    boolean isEmpty();
}
