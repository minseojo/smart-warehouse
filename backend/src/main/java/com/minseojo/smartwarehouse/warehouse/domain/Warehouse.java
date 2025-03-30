package com.minseojo.smartwarehouse.warehouse.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.minseojo.smartwarehouse.common.entity.BaseTimeEntity;
import com.minseojo.smartwarehouse.common.vo.LocatedObject;
import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import com.minseojo.smartwarehouse.wall.domain.Wall;
import com.minseojo.smartwarehouse.zone.domain.Zone;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@Getter
public class Warehouse extends BaseTimeEntity implements LocatedObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "position_x")),
            @AttributeOverride(name = "y", column = @Column(name = "position_y")),
            @AttributeOverride(name = "z", column = @Column(name = "position_z"))
    })
    private Position position;

    @Embedded
    private Size size;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "rotation_x")),
            @AttributeOverride(name = "y", column = @Column(name = "rotation_y")),
            @AttributeOverride(name = "z", column = @Column(name = "rotation_z")),
            @AttributeOverride(name = "w", column = @Column(name = "rotation_w"))
    })
    private Quaternion rotation;

    // === Read 전용 양방향 ===
    @OneToMany(mappedBy = "warehouse", fetch = FetchType.LAZY)
    @JsonManagedReference // 또는 DTO에서만 쓰기
    private List<Zone> zones = new ArrayList<>();

    @OneToMany(mappedBy = "warehouse", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Wall> walls = new ArrayList<>();

    public void update(String name, String description, Position position,
                       Quaternion rotation, Size size) {
        this.name = name;
        this.description = description;
        this.position = position;
        this.size = size;
        this.rotation = rotation;
    }

}
