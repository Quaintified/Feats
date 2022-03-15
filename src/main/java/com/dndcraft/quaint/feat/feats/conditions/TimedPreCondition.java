package com.dndcraft.quaint.feat.feats.conditions;

import com.dndcraft.quaint.feat.utils.BasicUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class TimedPreCondition extends PreCondition implements Runnable {
    public int timeOut = 0;
    public int reset;

    public TreeMap<Integer, List<Player>> timedPlayers = new TreeMap<>();

    public TimedPreCondition(String identifier, int timeOutInTicks) {
        super(identifier);
        this.isTimed = true;
        this.reset = timeOutInTicks;
        BasicUtils.startTimerRepeating(this, 0L, 1L);
    }

    @Override
    public void run() {
        if (timeOut <= 0 && timeOut != -1) {
            timeOut = reset;
            tick();
        }

        timeOut --;
    }

    public void tick() {
        TreeMap<Integer, List<Player>> newMap = new TreeMap<>();

        for (int ticksLeft : timedPlayers.keySet()) {
            int toBeLeft = ticksLeft - 1;
            if (toBeLeft <= 0) {
                for (Player player : timedPlayers.get(ticksLeft)) {
                    unconditionPlayer(player);
                }
            }
            newMap.put(toBeLeft, timedPlayers.get(ticksLeft));
        }

        timedPlayers = newMap;
    }

    public List<Player> getTimedPlayersWithTicksLeft(int ticksLeft) {
        return timedPlayers.get(ticksLeft);
    }

    public boolean timedPlayersContainsPlayer(Player player) {
        for (int i : timedPlayers.keySet()) {
            for (Player p : timedPlayers.get(i)) {
                if (p.equals(player)) return true;
            }
        }

        return false;
    }

    public void addTimedPlayer(Player player) {
        if (! conditionedPlayers.contains(player)) conditionPlayer(player);

        if (! timedPlayersContainsPlayer(player)) {
            List<Player> players = getTimedPlayersWithTicksLeft(reset);
            players.add(player);

            timedPlayers.put(reset, players);
        } else {
            removeTimedPlayer(player);
            addTimedPlayer(player);
        }
    }

    public void removeTimedPlayer(Player player) {
        if (conditionedPlayers.contains(player)) unconditionPlayer(player);

        if (timedPlayersContainsPlayer(player)) {
            for (int i : timedPlayers.keySet()) {
                List<Player> toSet = new ArrayList<>();

                for (Player p : new ArrayList<>(timedPlayers.get(i))) {
                    if (p.equals(player)) continue;

                    toSet.add(p);
                }

                timedPlayers.put(i, toSet);
            }
        }
    }
}
