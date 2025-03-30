package com.minseojo.smartwarehouse.charger.dto;

import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateChargerRequest {

    private String name;
    private Position position;
    private Size size;
    private Quaternion rotation;
    private Long zoneId;  // 속할 Zone의 ID
}
