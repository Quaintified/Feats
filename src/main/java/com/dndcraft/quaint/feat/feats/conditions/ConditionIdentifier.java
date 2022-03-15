package com.dndcraft.quaint.feat.feats.conditions;

public enum ConditionIdentifier {
    IN_AIR("in-air"),
    ON_GROUND("on-ground"),
    ;

    public final String name;

    ConditionIdentifier(String name) {
        this.name = name;
    }
}
