package com.minseojo.smartwarehouse.common.vo;

import com.minseojo.smartwarehouse.common.util.RotationMatrixUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Quaternion {

    private double x;
    private double y;
    private double z;
    private double w;

    public static Quaternion of(Quaternion other) {
        return new Quaternion(other.x, other.y, other.z, other.w);
    }

    public static Quaternion identity() {
        return new Quaternion(0, 0, 0, 1);
    }

    public Quaternion normalize() {
        double len = Math.sqrt(x * x + y * y + z * z + w * w);
        return new Quaternion(x / len, y / len, z / len, w / len);
    }

    public Vector3[] toLocalAxis() {
        return RotationMatrixUtils.getLocalAxes(this);
    }
}
