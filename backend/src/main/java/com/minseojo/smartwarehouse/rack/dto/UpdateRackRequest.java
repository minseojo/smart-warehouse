package com.minseojo.smartwarehouse.rack.dto;

import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of", access = AccessLevel.PUBLIC)
@Builder
@Getter
public class UpdateRackRequest {
    private String name;
    private String description;
    private Position position;
    private Size size;
    private Quaternion rotation;
}