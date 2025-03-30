package com.minseojo.smartwarehouse.common.util;

import com.minseojo.smartwarehouse.common.vo.Quaternion;
import com.minseojo.smartwarehouse.common.vo.Vector3;

public class RotationMatrixUtils {

    // 쿼터니언으로부터 Local Axis (X, Y, Z) 반환
    public static Vector3[] getLocalAxes(Quaternion q) {
        q = q.normalize();

        double xx = q.getX() * q.getX();
        double yy = q.getY() * q.getY();
        double zz = q.getZ() * q.getZ();
        double xy = q.getX() * q.getY();
        double xz = q.getX() * q.getZ();
        double yz = q.getY() * q.getZ();
        double wx = q.getW() * q.getX();
        double wy = q.getW() * q.getY();
        double wz = q.getW() * q.getZ();

        // Local Axis
        Vector3 xAxis = new Vector3(
                1 - 2 * (yy + zz),
                2 * (xy + wz),
                2 * (xz - wy)
        ).normalize();

        Vector3 yAxis = new Vector3(
                2 * (xy - wz),
                1 - 2 * (xx + zz),
                2 * (yz + wx)
        ).normalize();

        Vector3 zAxis = new Vector3(
                2 * (xz + wy),
                2 * (yz - wx),
                1 - 2 * (xx + yy)
        ).normalize();

        return new Vector3[] {xAxis, yAxis, zAxis};
    }
}
