package com.dndcraft.quaint.feat.feats.custom;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LatchedRunnable implements Runnable{
    public Player player;
    public Location latchedTo;

    public LatchedRunnable(Player player, Location latchedTo) {
        this.player = player;
        this.latchedTo = latchedTo;
    }

    @Override
    public void run() {
        if (! player.isSneaking()) return;

        player.teleportAsync(latchedTo);
    }
}
