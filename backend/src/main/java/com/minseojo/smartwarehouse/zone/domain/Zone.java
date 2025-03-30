package com.minseojo.smartwarehouse.zone.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.minseojo.smartwarehouse.common.entity.BaseTimeEntity;
import com.minseojo.smartwarehouse.common.vo.*;
import com.minseojo.smartwarehouse.warehouse.domain.Warehouse;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@Getter
public class Zone extends BaseTimeEntity implements LocatedObject {

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

    @Enumerated(EnumType.STRING)
    private ZoneType type;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Color color = Color.RED; // 기본 컬러 설정

    // Write 용 단방향
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    @JsonBackReference // 혹은 DTO에서 무시
    private Warehouse warehouse;

    public void update(
            String name,
            String description,
            Position position,
            Size size,
            Quaternion rotation,
            ZoneType type,
            Color color,
            Warehouse warehouse
    ) {
        this.name = name;
        this.description = description;
        this.position = position;
        this.size = size;
        this.rotation = rotation;
        this.type = type;
        this.color = color;
        this.warehouse = warehouse;
    }
}
