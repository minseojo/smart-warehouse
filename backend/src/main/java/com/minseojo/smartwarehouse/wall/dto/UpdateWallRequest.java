package com.minseojo.smartwarehouse.wall.dto;

import com.minseojo.smartwarehouse.common.vo.Color;
import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Size;
import lombok.Getter;

@Getter
public class UpdateWallRequest {
    private String description;
    private Position position;
    private Size size;
    private Quaternion rotation;
    private Color color;
}
