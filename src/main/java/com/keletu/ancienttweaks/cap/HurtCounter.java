package com.keletu.ancienttweaks.cap;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

public class HurtCounter implements INBTSerializable<CompoundTag> {

    private double hurtCounter;
    private int hurtSinceLastDamage;

    public double getHurtCounter() {
        return hurtCounter;
    }

    public void setHurtCounter(double hurtCounter) {
        this.hurtCounter = hurtCounter;
    }

    public int getHurtSinceLastDamage() {
        return hurtSinceLastDamage;
    }

    public void setHurtSinceLastDamage(int hurtSinceLastDamage) {
        this.hurtSinceLastDamage = hurtSinceLastDamage;
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("hurtCounter", this.hurtCounter);
        tag.putInt("damageCounter", this.hurtSinceLastDamage);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        this.hurtCounter = tag.getDouble("hurtCounter");
        this.hurtSinceLastDamage = tag.getInt("damageCounter");
    }
}