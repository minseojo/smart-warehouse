package com.minseojo.smartwarehouse.zone;

import com.minseojo.smartwarehouse.zone.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {

    Optional<Zone> findByName(String name);
}