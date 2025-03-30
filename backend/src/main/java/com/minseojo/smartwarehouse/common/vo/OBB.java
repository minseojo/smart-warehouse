package com.minseojo.smartwarehouse.common.vo;

import com.minseojo.smartwarehouse.common.util.RotationMatrixUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class OBB {

    private Vector3 center;
    private Vector3 axisX;
    private Vector3 axisY;
    private Vector3 axisZ;
    private double halfWidth;
    private double halfHeight;
    private double halfDepth;

    public static OBB from(Position position, Quaternion quaternion, Size size) {
        Vector3[] axes = RotationMatrixUtils.getLocalAxes(quaternion);
        return new OBB(
                new Vector3(position.getX(), position.getY(), position.getZ()),
                axes[0],
                axes[1],
                axes[2],
                size.getWidth() / 2,
                size.getHeight() / 2,
                size.getDepth() / 2
        );
    }

    // OBB-OBB 충돌
    public boolean intersects(OBB other) {
        Vector3[] axes = {
                this.axisX, this.axisY, this.axisZ,
                other.axisX, other.axisY, other.axisZ,
                this.axisX.cross(other.axisX),
                this.axisX.cross(other.axisY),
                this.axisX.cross(other.axisZ),
                this.axisY.cross(other.axisX),
                this.axisY.cross(other.axisY),
                this.axisY.cross(other.axisZ),
                this.axisZ.cross(other.axisX),
                this.axisZ.cross(other.axisY),
                this.axisZ.cross(other.axisZ)
        };

        for (Vector3 axis : axes) {
            if (axis.isZero()) continue;
            if (!overlapOnAxis(this, other, axis)) return false;
        }
        return true;
    }

    private boolean overlapOnAxis(OBB a, OBB b, Vector3 axis) {
        double projA = getProjectionRadius(a, axis);
        double projB = getProjectionRadius(b, axis);
        double distance = Math.abs(axis.dot(b.center.subtract(a.center)));
        return distance <= (projA + projB);
    }

    private double getProjectionRadius(OBB obb, Vector3 axis) {
        return obb.halfWidth * Math.abs(axis.dot(obb.axisX)) +
                obb.halfHeight * Math.abs(axis.dot(obb.axisY)) +
                obb.halfDepth * Math.abs(axis.dot(obb.axisZ));
    }
}
