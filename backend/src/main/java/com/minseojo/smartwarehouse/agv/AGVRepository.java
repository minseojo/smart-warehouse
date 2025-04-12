package com.minseojo.smartwarehouse.agv;

import com.minseojo.smartwarehouse.agv.entity.AGV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AGVRepository extends JpaRepository<AGV, Long> {
    List<AGV> findByWarehouseId(Long warehouseId);
}
