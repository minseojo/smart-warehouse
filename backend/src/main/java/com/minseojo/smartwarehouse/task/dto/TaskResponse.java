package com.minseojo.smartwarehouse.task.dto;

import com.minseojo.smartwarehouse.task.entity.Task;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TaskResponse {

    private Long id;
    private String description;
    private Long zoneId;

    public static TaskResponse from(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .description(task.getDescription())
                .zoneId(task.getZone().getId())
                .build();
    }
}
