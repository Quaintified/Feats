package com.dndcraft.quaint.feat.feats.custom.wallhang;

import com.dndcraft.quaint.feat.feats.FeatBase;
import com.dndcraft.quaint.feat.feats.custom.wallclimb.LatchedPlayer;
import com.dndcraft.quaint.feat.utils.MathUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class FeatWallHang extends FeatBase {
    public FeatWallHang() {
        super("wall-hang", "Wall Hang", "Allows you to hang off of a wall!");
    }

    @EventHandler
    public void grabbing(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Location player_loc = player.getLocation();

        ItemStack stack = event.getItem();
        if (stack == null) return;
        if (! stack.getType().equals(Material.TRIDENT)) return;

        Block block = event.getClickedBlock();
        if (block == null) return;
        if (block.isLiquid()) return;

        if (MathUtils.getDistance3D(player_loc, block.getLocation()) > 0.5d) return;

        if (! block.getLocation().add(0, 1, 0).getBlock().getType().equals(Material.AIR)) return;
        if (! block.getLocation().add(0, 2, 0).getBlock().getType().equals(Material.AIR)) return;

        if (! player.isSneaking()) return;

        player.teleportAsync(player_loc);
    }
}
