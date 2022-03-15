package com.dndcraft.quaint.feat.feats.conditions;

import java.util.ArrayList;
import java.util.List;

public class ConditionHandler {
    public static List<PreCondition> preConditions = new ArrayList<>();

    public static void registerPreCondition(PreCondition preCondition) {
        if (preConditionsContainsConditionBy(preCondition.identifier, preCondition.isTimed)) return;

        preConditions.add(preCondition);
    }

    public static void registerPreConditions(PreCondition... toRegister) {
        for (PreCondition preCondition : toRegister) {
            registerPreCondition(preCondition);
        }
    }

    public static boolean preConditionsContainsConditionBy(String name, boolean isTimed) {
        for (PreCondition preCondition : preConditions) {
            if (preCondition.identifier.equals(name) && preCondition.isTimed == isTimed) return true;
        }

        return false;
    }

    public static PreCondition getPreConditionBy(String name, boolean isTimed) {
        for (PreCondition preCondition : preConditions) {
            if (preCondition.identifier.equals(name) && preCondition.isTimed == isTimed) return preCondition;
        }

        return null;
    }
}
