package com.dndcraft.quaint.feat.utils;

import org.bukkit.Location;

public class MathUtils {
    public static double getDistance3D(Location point1, Location point2) {
        return Math.sqrt(Math.pow(point2.getX() - point1.getX(), 2d) + Math.pow(point2.getY() - point1.getY(), 2d) + Math.pow(point2.getZ() - point1.getZ(), 2d));
    }

    public static double getDistanceY(Location point1, Location point2) {
        return point2.getY() - point1.getY();
    }
}
