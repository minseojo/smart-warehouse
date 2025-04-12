package com.minseojo.smartwarehouse.task;

import com.minseojo.smartwarehouse.task.entity.Task;

public interface TaskDispatcher {
    void dispatch(Task task);
}
