package com.keletu.ancienttweaks.init;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.baubles.potion.PotionCleansing;
import com.keletu.ancienttweaks.baubles.potion.PotionCrumbling;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ATEffects {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, AncientTweaks.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> CRUMBLING = EFFECTS.register("crumbling", PotionCrumbling::new);
    public static final DeferredHolder<MobEffect, MobEffect> CLEANSING = EFFECTS.register("cleansing", PotionCleansing::new);
}
