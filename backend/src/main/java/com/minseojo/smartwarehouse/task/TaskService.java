package com.minseojo.smartwarehouse.task;

import com.minseojo.smartwarehouse.task.dto.CreateTaskRequest;
import com.minseojo.smartwarehouse.zone.ZoneRepository;
import com.minseojo.smartwarehouse.zone.domain.Zone;
import com.minseojo.smartwarehouse.zone.domain.ZoneType;
import com.minseojo.smartwarehouse.task.domain.Task;
import com.minseojo.smartwarehouse.task.dto.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ZoneRepository zoneRepository;

    public TaskResponse create(CreateTaskRequest dto) {
        Zone zone = zoneRepository.findById(dto.getZoneId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Zone"));

        if (!(zone.getType() == ZoneType.INBOUND || zone.getType() == ZoneType.OUTBOUND)) {
            throw new IllegalArgumentException("Task는 INBOUND, OUTBOUND Zone에만 배치할 수 있습니다.");
        }

        Task task = Task.builder()
                .description(dto.getDescription())
                .zone(zone)
                .build();

        return TaskResponse.from(taskRepository.save(task));
    }
}
