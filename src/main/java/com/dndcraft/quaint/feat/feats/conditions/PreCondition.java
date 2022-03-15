package com.dndcraft.quaint.feat.feats.conditions;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PreCondition {
    public String identifier;
    public boolean isTimed = false;
    public List<Player> conditionedPlayers = new ArrayList<>();

    public PreCondition(String identifier) {
        this.identifier = identifier;
    }

    public void conditionPlayer(Player player) {
        if (! conditionedPlayers.contains(player)) conditionedPlayers.add(player);
    }

    public void unconditionPlayer(Player player) {
        conditionedPlayers.remove(player);
    }

    public boolean isCurrentlyDone(Player player) {
        return conditionedPlayers.contains(player);
    }
}
