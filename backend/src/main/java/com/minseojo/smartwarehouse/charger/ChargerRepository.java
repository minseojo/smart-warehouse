package com.minseojo.smartwarehouse.charger;

import com.minseojo.smartwarehouse.charger.domain.Charger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargerRepository extends JpaRepository<Charger, Long> {
}
