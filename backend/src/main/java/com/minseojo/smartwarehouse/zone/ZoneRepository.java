package com.minseojo.smartwarehouse.zone;

import com.minseojo.smartwarehouse.zone.domain.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {
    List<Zone> findByWarehouseId(Long warehouseId);
}