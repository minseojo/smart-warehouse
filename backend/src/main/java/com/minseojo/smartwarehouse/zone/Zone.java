package com.minseojo.smartwarehouse.zone;

import com.minseojo.smartwarehouse.rack.Rack;
import com.minseojo.smartwarehouse.warehouse.Warehouse;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Zone {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ZoneType type; // INBOUND, OUTBOUND, STORAGE 등

    @ManyToOne
    private Warehouse warehouse;

    @OneToMany(mappedBy = "zone", cascade = CascadeType.ALL)
    private List<Rack> racks = new ArrayList<>();
}
