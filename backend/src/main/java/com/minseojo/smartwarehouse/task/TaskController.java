package com.minseojo.smartwarehouse.task;

import com.minseojo.smartwarehouse.task.dto.CreateTaskRequest;
import com.minseojo.smartwarehouse.task.dto.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskResponse create(@RequestBody CreateTaskRequest dto) {
        return taskService.create(dto);
    }
}
