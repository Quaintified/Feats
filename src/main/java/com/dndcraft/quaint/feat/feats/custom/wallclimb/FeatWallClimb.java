package com.dndcraft.quaint.feat.feats.custom.wallclimb;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import com.dndcraft.quaint.feat.Feat;
import com.dndcraft.quaint.feat.feats.FeatBase;
import com.dndcraft.quaint.feat.feats.conditions.ConditionHandler;
import com.dndcraft.quaint.feat.feats.conditions.ConditionIdentifier;
import com.dndcraft.quaint.feat.feats.conditions.PreCondition;
import com.dndcraft.quaint.feat.feats.conditions.TimedPreCondition;
import com.dndcraft.quaint.feat.utils.BasicUtils;
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

    public FeatWallClimb() {
        super("wall-climb", "Wall Climb", "Allows you to jump up and climb over a wall!");
    }

    @EventHandler
    public void grabbing(PlayerInteractEvent event) {
        Feat.get().getLogger().info("WC Interacted!");

        Player player = event.getPlayer(); // Player.
        TimedPreCondition condition = (TimedPreCondition) ConditionHandler.getPreConditionBy(ConditionIdentifier.ON_GROUND.name, true);
        if (condition == null) return;
        if (! condition.isCurrentlyDone(player)) return;

        BasicUtils.info("WC Condition: " + condition.conditionedPlayers);
        BasicUtils.info("WC Condition: " + condition.timedPlayers.values());

        Location player_loc = player.getLocation(); // Player's location.

        // Get the item that they clicked with and check if it is null or NOT a trident.
        ItemStack stack = event.getItem();
        if (stack != null) {
            if (! stack.getType().equals(Material.AIR)) return;
        }
        BasicUtils.putGrabberHand(player);

        // Get the clicked block and check if it is null or liquid.
        Block block = event.getClickedBlock();
        if (block == null) {
            BasicUtils.removeAllGrabbers(player);
            return;
        }
        if (block.isLiquid()) {
            BasicUtils.removeAllGrabbers(player);
            return;
        }

        if (MathUtils.getDistance2DXZ(player_loc, MathUtils.getCenteredLocation(block.getLocation())) > 1d) {
            BasicUtils.removeAllGrabbers(player);
            return;
        }

        // Check if block above hang are air (empty).
        if (! block.getLocation().add(0, 1, 0).getBlock().getType().equals(Material.AIR)) {
            BasicUtils.removeAllGrabbers(player);
            return;
        }
        if (! block.getLocation().add(0, 2, 0).getBlock().getType().equals(Material.AIR)) {
            BasicUtils.removeAllGrabbers(player);
            return;
        }

        // Check if the player is sneaking.
        if (! player.isSneaking()) {
            BasicUtils.removeAllGrabbers(player);
            return;
        }

        // Latch the player. (Create the wall hang.)
        if (! isPlayerLatched(player)) {
            latchedPlayers.add(new LatchedPlayer(player,
                    MathUtils.getWithYCenteredOnDifferentBlock(
                            player_loc,
                            MathUtils.getBlockBelow(block)
                    ),
                    MathUtils.asPlayerRotation(
                            MathUtils.getCenteredLocation(
                                    MathUtils.getLocationAbove(block.getLocation())
                            ),
                            player
                    )
            ));
        }

        Feat.get().getLogger().info("WC Interaction Done!");
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

    @EventHandler
    public void throwTrident(PlayerLaunchProjectileEvent event) {
        ItemStack stack = event.getItemStack();
        if (BasicUtils.isGrabber(stack)) event.setCancelled(true);
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
