package com.minseojo.smartwarehouse.wall;

import com.minseojo.smartwarehouse.wall.entity.Wall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WallRepository extends JpaRepository<Wall, Long> {
}
