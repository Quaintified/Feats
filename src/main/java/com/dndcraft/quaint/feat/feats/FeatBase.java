package com.dndcraft.quaint.feat.feats;

import com.dndcraft.quaint.feat.Feat;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class FeatBase implements Listener {
    public String identifier;
    public String name;
    public String description;

    public FeatBase(String identifier, String name, String description) {
        this.identifier = identifier;
        this.name = name;
        this.description = description;

        Bukkit.getPluginManager().registerEvents(this, Feat.get());
    }

    public FeatBase setName(String name) {
        this.name = name;
        return this;
    }

    public FeatBase setDescription(String description) {
        this.description = description;
        return this;
    }
}
