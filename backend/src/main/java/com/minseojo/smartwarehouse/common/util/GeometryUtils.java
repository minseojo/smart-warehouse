package com.minseojo.smartwarehouse.common.util;

import com.minseojo.smartwarehouse.common.vo.Position;
import com.minseojo.smartwarehouse.common.vo.Size;

public class GeometryUtils {

    // Zone 내부 판정
    public static boolean isPointInsideBox(Position point, Position boxPos, Size boxSize) {
        return point.getX() >= boxPos.getX() && point.getX() <= boxPos.getX() + boxSize.getWidth()
                && point.getY() >= boxPos.getY() && point.getY() <= boxPos.getY() + boxSize.getHeight()
                && point.getZ() >= boxPos.getZ() && point.getZ() <= boxPos.getZ() + boxSize.getDepth();
    }

    // 충돌 검사 (box vs box)
    public static boolean isBoxIntersect(Position p1, Size s1, Position p2, Size s2) {
        return (p1.getX() < p2.getX() + s2.getWidth() && p1.getX() + s1.getWidth() > p2.getX()) &&
                (p1.getY() < p2.getY() + s2.getHeight() && p1.getY() + s1.getHeight() > p2.getY()) &&
                (p1.getZ() < p2.getZ() + s2.getDepth() && p1.getZ() + s1.getDepth() > p2.getZ());
    }

    // 충돌 검사 (sphere vs sphere)
    public static boolean isSphereIntersect(Position c1, double r1, Position c2, double r2) {
        double distance = c1.distanceTo(c2);
        return distance <= (r1 + r2);
    }
}
