package com.dndcraft.quaint.feat.feats.custom.wallclimb;

import com.dndcraft.quaint.feat.Feat;
import com.dndcraft.quaint.feat.feats.custom.TurnableLatchedRunnable;
import com.dndcraft.quaint.feat.utils.BasicUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LatchedPlayer {
    public Player player;
    public int taskIdNumber;
    public Location dropAt;

    public LatchedPlayer(Player player, Location location, Location dropAt) {
        this.player = player;
        this.taskIdNumber = Bukkit.getScheduler().scheduleSyncRepeatingTask(Feat.get(), new TurnableLatchedRunnable(player, location), 0L, 1L);
        this.dropAt = dropAt;
    }

    public void cancel() {
        Bukkit.getScheduler().cancelTask(taskIdNumber);
        player.teleportAsync(dropAt);
        BasicUtils.removeAllGrabbers(player);
    }
}
