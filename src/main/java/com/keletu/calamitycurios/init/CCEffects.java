package com.keletu.calamitycurios.init;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.baubles.potion.PotionCleansing;
import com.keletu.calamitycurios.baubles.potion.PotionCrumbling;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CCEffects {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, CalamityCurios.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> CRUMBLING = EFFECTS.register("crumbling", PotionCrumbling::new);
    public static final DeferredHolder<MobEffect, MobEffect> CLEANSING = EFFECTS.register("cleansing", PotionCleansing::new);
}
