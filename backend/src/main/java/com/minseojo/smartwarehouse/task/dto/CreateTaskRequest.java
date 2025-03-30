package com.minseojo.smartwarehouse.task.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateTaskRequest {

    private String description;
    private Long zoneId;  // 속할 Zone의 ID
}
