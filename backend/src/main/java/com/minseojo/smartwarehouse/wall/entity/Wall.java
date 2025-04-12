package com.minseojo.smartwarehouse.wall.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.minseojo.smartwarehouse.common.vo.Color;
import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import com.minseojo.smartwarehouse.warehouse.entity.Warehouse;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@Getter
public class Wall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @Builder.Default // 기본 컬러 설정
    private Color color = Color.OUTER_WALL_GRAY;

    // Write 용 단방향
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    @JsonBackReference // 혹은 DTO에서 무시
    private Warehouse warehouse;

    public void update(String description, Position position, Size size, Quaternion rotation, Color color) {
        this.description = description;
        this.position = position;
        this.size = size;
        this.rotation = rotation;
        this.color = color;
    }

}
