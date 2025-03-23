package com.minseojo.smartwarehouse.rack;

import com.minseojo.smartwarehouse.zone.Zone;
import jakarta.persistence.*;

@Entity
public class Rack {
    @Id @GeneratedValue
    private Long id;

    private String rackCode;
    private int x;
    private int y;
    private int z;

    @ManyToOne
    private Zone zone;
}

