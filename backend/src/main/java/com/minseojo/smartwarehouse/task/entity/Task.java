package com.minseojo.smartwarehouse.task.entity;

import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.zone.entity.Zone;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private TaskType type;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "source_x")),
            @AttributeOverride(name = "y", column = @Column(name = "source_y")),
            @AttributeOverride(name = "z", column = @Column(name = "source_z"))
    })
    Position source;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "destination_x")),
            @AttributeOverride(name = "y", column = @Column(name = "destination_y")),
            @AttributeOverride(name = "z", column = @Column(name = "destination_z"))
    })
    Position destination;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id")
    private Zone zone; // 단방향만 설정

}
