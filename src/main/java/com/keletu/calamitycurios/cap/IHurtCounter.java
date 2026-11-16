package com.keletu.calamitycurios.cap;

import net.minecraft.nbt.CompoundTag;

public interface IHurtCounter {

    double getHurtCounter();

    void setHurtCounter(double value);

    int getHurtSinceLastDamage();

    void setHurtSinceLastDamage(int value);

    CompoundTag save();

    void load(CompoundTag tag);
}