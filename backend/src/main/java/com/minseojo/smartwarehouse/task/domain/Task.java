package com.minseojo.smartwarehouse.task.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Task {
    private String description;
    private boolean urgent;
    private int estimatedDistance; // 단위: meter
    private int priority; // 1 (최상) ~ 5 (최하)
    private int estimatedTime; // 단위: seconds
}

