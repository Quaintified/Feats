package com.dndcraft.quaint.feat.feats.custom.wallclimb;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.dndcraft.quaint.feat.feats.FeatBase;
import com.dndcraft.quaint.feat.utils.MathUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FeatWallClimb extends FeatBase {
    public List<LatchedPlayer> latchedPlayers = new ArrayList<>();
    public List<Player> jumpingPlayers = new ArrayList<>();

    public FeatWallClimb() {
        super("wall-climb", "Wall Climb", "Allows you to jump up and climb over a wall!");
    }

    @EventHandler
    public void onJump(PlayerJumpEvent event) {
        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        jumpingPlayers.remove(player);

        Location from_loc = event.getFrom();
        Location player_loc = player.getLocation();

        double distance = MathUtils.getDistanceY(from_loc, player_loc);
        if (distance < 1d) return;
        if (! player.isSneaking()) return;

        jumpingPlayers.add(player);
    }

    @EventHandler
    public void grabbing(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (! jumpingPlayers.contains(player)) return;

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

        latchedPlayers.add(new LatchedPlayer(player, player_loc, block.getLocation().add(0, 1, 0)));
        jumpingPlayers.remove(player);
    }

    @EventHandler
    public void unShift(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (! event.isSneaking()) {
            if (isPlayerLatched(player)) {
                LatchedPlayer latchedPlayer = getLatchedPlayer(player);
                latchedPlayer.cancel();
                latchedPlayers.remove(latchedPlayer);
            }
        }
    }

    public boolean isPlayerLatched(Player player) {
        for (LatchedPlayer latchedPlayer : latchedPlayers) {
            if (latchedPlayer.player.equals(player)) return true;
        }

        return false;
    }

    public LatchedPlayer getLatchedPlayer(Player player) {
        for (LatchedPlayer latchedPlayer : latchedPlayers) {
            if (latchedPlayer.player.equals(player)) return latchedPlayer;
        }

        return null;
    }
}
