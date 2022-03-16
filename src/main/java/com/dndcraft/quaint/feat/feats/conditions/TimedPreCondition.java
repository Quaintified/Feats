package com.dndcraft.quaint.feat.feats.conditions;

import com.dndcraft.quaint.feat.Feat;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class TimedPreCondition extends PreCondition implements Runnable {
    public int timeOut;

    public HashMap<Player, Integer> timedPlayers = new HashMap<>();

    public TimedPreCondition(String identifier, int timeOutInTicks) {
        super(identifier);
        this.isTimed = true;
        this.timeOut = timeOutInTicks;
        Feat.get().getServer().getScheduler().scheduleSyncRepeatingTask(Feat.get(), this, 0, 1);
    }

    @Override
    public void run() {
        HashMap<Player, Integer> newMap = new HashMap<>();

        for (Player player : timedPlayers.keySet()) {
            int toBeLeft = timedPlayers.get(player) - 1;
            if (toBeLeft <= 0) {
                unconditionPlayer(player);
                continue;
            }
            newMap.put(player, toBeLeft);
        }

        timedPlayers = newMap;
    }
//    public List<Player> getTimedPlayersWithTicksLeft(int ticksLeft) {
//        List<Player> players = timedPlayers.get(ticksLeft);
//        return players == null ? new ArrayList<>() : players;
//    }

    public boolean timedPlayersContainsPlayer(Player player) {
        return timedPlayers.containsKey(player);
    }

    public void addTimedPlayer(Player player) {
        if (! conditionedPlayers.contains(player)) conditionPlayer(player);
        timedPlayers.put(player, timeOut);
    }

    public void removeTimedPlayer(Player player) {
        if (conditionedPlayers.contains(player)) unconditionPlayer(player);
        timedPlayers.remove(player);
    }
}
