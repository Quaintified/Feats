package com.dndcraft.quaint.feat.feats.custom;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class TurnableLatchedRunnable implements Runnable{
    public Player player;
    public Location latchedTo;

    public TurnableLatchedRunnable(Player player, Location latchedTo) {
        this.player = player;
        this.latchedTo = latchedTo;
    }

    @Override
    public void run() {
        if (! player.isSneaking()) return;

//        latchedTo.setYaw(player.getLocation().getYaw());
//        latchedTo.setPitch(player.getLocation().getPitch());

        player.teleportAsync(latchedTo.setDirection(player.getLocation().getDirection()));
    }
}
