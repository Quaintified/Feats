package com.dndcraft.quaint.feat;

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
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
