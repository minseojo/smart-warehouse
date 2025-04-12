package com.minseojo.smartwarehouse.common.vo;

import com.minseojo.smartwarehouse.common.util.RotationMatrixUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quaternion that = (Quaternion) o;
        return Double.compare(x, that.x) == 0 && Double.compare(y, that.y) == 0 && Double.compare(z, that.z) == 0 && Double.compare(w, that.w) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }
}
