package com.dndcraft.quaint.feat;

import com.dndcraft.quaint.feat.feats.conditions.ConditionHandler;
import com.dndcraft.quaint.feat.feats.conditions.ConditionIdentifier;
import com.dndcraft.quaint.feat.feats.conditions.TimedPreCondition;
import com.dndcraft.quaint.feat.feats.custom.wallclimb.FeatWallClimb;
import com.dndcraft.quaint.feat.feats.custom.wallhang.FeatWallHang;
import com.dndcraft.quaint.feat.listeners.DefaultListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Feat extends JavaPlugin {
    private static Feat inst;

    public static Feat get() {
        return inst;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        inst = this;

        // Handle Conditions.
        ConditionHandler.registerPreConditions(
                new TimedPreCondition(ConditionIdentifier.ON_GROUND.name, 20)
        );

        // Register listeners.
        new DefaultListener();

        // Register the Feats.
        registerFeats();
    }

    public void registerFeats() {
        new FeatWallClimb();
        new FeatWallHang();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
