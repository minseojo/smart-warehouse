package com.minseojo.smartwarehouse.rack.domain;

import com.minseojo.smartwarehouse.common.entity.BaseTimeEntity;
import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import com.minseojo.smartwarehouse.zone.domain.Zone;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@Getter
public class Rack extends BaseTimeEntity {

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

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id")
    private Zone zone; // 단방향만 설정

}
