package com.minseojo.smartwarehouse.agv;

import com.minseojo.smartwarehouse.agv.entity.AGV;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AGVManager {

    private final Map<Long, AGV> agvMap = new ConcurrentHashMap<>();

    public void register(AGV agv) {
        agvMap.put(agv.getId(), agv);
    }

    public Collection<AGV> getAll() {
        return agvMap.values();
    }

    public AGV getById(Long id) {
        return agvMap.get(id);
    }

    public void clearAll() {
        agvMap.clear();
    }
}
