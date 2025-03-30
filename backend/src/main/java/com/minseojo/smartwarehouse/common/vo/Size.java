package com.minseojo.smartwarehouse.common.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class Size {

    private double width;
    private double height;
    private double depth;

    public static Size of(Size other) {
        return new Size(other.width, other.height, other.depth);
    }
}
