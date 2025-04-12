package com.minseojo.smartwarehouse.rack;

import com.minseojo.smartwarehouse.rack.entity.Rack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RackRepository extends JpaRepository<Rack, Long> {
}
