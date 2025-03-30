package com.minseojo.smartwarehouse.warehouse.dto;

import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of", access = AccessLevel.PUBLIC)
@Builder
@Getter
public class CreateWarehouseRequest {
    private String name;
    private String description;
    private Position position;
    private Quaternion rotation;
    private Size size;
}
