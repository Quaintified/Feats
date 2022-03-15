package com.dndcraft.quaint.feat.utils;

import com.dndcraft.quaint.feat.Feat;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class MathUtils {
    public static double getDistance3D(Location point1, Location point2) {
        return Math.sqrt(Math.pow(point2.getX() - point1.getX(), 2d) + Math.pow(point2.getY() - point1.getY(), 2d) + Math.pow(point2.getZ() - point1.getZ(), 2d));
    }

    public static double getDistance2DXZ(Location point1, Location point2) {
        return Math.sqrt(Math.pow(point2.getX() - point1.getX(), 2d) + Math.pow(point2.getZ() - point1.getZ(), 2d));
    }

    public static double getDistanceY(Location point1, Location point2) {
        return point2.getY() - point1.getY();
    }

    public static Location getCenteredLocation(Location location) {
        int xi = location.getBlockX();
        int yi = location.getBlockY();
        int zi = location.getBlockZ();

        double x = xi + 0.5;
        double y = yi + 0.5;
        double z = zi + 0.5;

        return new Location(location.getWorld(), x, y, z);
    }

    public static Location getWithYCenteredLocation(Location location) {
        int yi = location.getBlockY();
        double y = yi + 0.5;
        return new Location(location.getWorld(), location.getX(), y, location.getZ());
    }

    public static Location getWithYCenteredOnDifferentBlock(Location location, Block block) {
        Location blockLoc = getWithYCenteredLocation(block.getLocation());
        return location.set(location.getX(), blockLoc.getY(), location.getZ());
    }

    public static Block getBlockBelow(Block block) {
        return block.getLocation().add(0, -1, 0).getBlock();
    }

    public static Block getBlockAbove(Block block) {
        return block.getLocation().add(0, 1, 0).getBlock();
    }

    public static Location getLocationkBelow(Location location) {
        return location.add(0, -1, 0);
    }

    public static Location getLocationAbove(Location location) {
        return location.add(0, 1, 0);
    }

    public static Location asPlayerRotation(Location location, Player player) {
        return location.setDirection(player.getLocation().getDirection());
    }
}
