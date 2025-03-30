package com.minseojo.smartwarehouse.wall;

import com.minseojo.smartwarehouse.wall.domain.Wall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WallRepository extends JpaRepository<Wall, Long> {
}
