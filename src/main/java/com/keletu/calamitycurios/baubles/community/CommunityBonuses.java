package com.keletu.calamitycurios.baubles.community;

import java.util.Collection;

public record CommunityBonuses(int stacks) {

    public CommunityBonuses {
        stacks = Math.max(0, stacks);
    }

    public static CommunityBonuses forProgress(int uniqueBosses, int maximum) {
        return new CommunityBonuses(Math.min(Math.max(0, uniqueBosses), Math.max(0, maximum)));
    }

    public double maxHealth() {
        return stacks;
    }

    public double attackDamage() {
        return stacks * 0.5D;
    }

    public double movementSpeed() {
        return stacks * 0.002D;
    }

    public double jumpStrength() {
        return stacks * 0.002D;
    }

    public double safeFallDistance() {
        return stacks * 0.002D;
    }

    public double damageMultiplier() {
        return Math.pow(0.99D, stacks);
    }

    public static boolean isBonusHealthPoint(int point, int firstBonusPoint, int maximumPoint) {
        return point >= firstBonusPoint && point < maximumPoint;
    }

    public static boolean allowsBoss(String entityId, boolean taggedBoss,
                                     Collection<? extends String> whitelist,
                                     Collection<? extends String> blacklist) {
        return !blacklist.contains(entityId)
                && (whitelist.isEmpty() ? taggedBoss : whitelist.contains(entityId));
    }
}
