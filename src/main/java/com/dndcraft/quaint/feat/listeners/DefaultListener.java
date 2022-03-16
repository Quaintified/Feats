package com.dndcraft.quaint.feat.listeners;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.dndcraft.quaint.feat.feats.conditions.ConditionHandler;
import com.dndcraft.quaint.feat.feats.conditions.ConditionIdentifier;
import com.dndcraft.quaint.feat.feats.conditions.PreCondition;
import com.dndcraft.quaint.feat.feats.conditions.TimedPreCondition;
import com.dndcraft.quaint.feat.utils.BasicUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class DefaultListener implements Listener {
    public DefaultListener() {
        BasicUtils.registerListener(this);

        BasicUtils.info("DefaultListener loaded!");
    }

    @EventHandler
    public void onJump(PlayerMoveEvent event) {
        if (event.isCancelled()) return;

        Player player = event.getPlayer();

//        BasicUtils.info("Event Fired!");

        if (player.isOnGround()) {
            TimedPreCondition condition = (TimedPreCondition) ConditionHandler.getPreConditionBy(ConditionIdentifier.ON_GROUND.name, true);
            if (condition == null) return;
            condition.addTimedPlayer(player);
        }
    }
}
