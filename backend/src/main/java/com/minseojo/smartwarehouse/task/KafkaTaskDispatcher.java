//package com.minseojo.smartwarehouse.task;
//
//import com.minseojo.smartwarehouse.task.entity.Task;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class KafkaTaskDispatcher implements TaskDispatcher {
//
//    private final KafkaTemplate<String, Task> kafkaTemplate;
//
//    @Override
//    public void dispatch(Task task) {
//        kafkaTemplate.send("task-assignment", task.getAgvId(), task);
//    }
//}
