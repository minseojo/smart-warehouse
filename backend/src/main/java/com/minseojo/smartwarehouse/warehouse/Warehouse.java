package com.minseojo.smartwarehouse.warehouse;

import com.minseojo.smartwarehouse.zone.Zone;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Warehouse {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int width;
    private int height;
    private int depth;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private List<Zone> zones = new ArrayList<>();
}
