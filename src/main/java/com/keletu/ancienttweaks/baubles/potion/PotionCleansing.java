package com.keletu.ancienttweaks.baubles.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class PotionCleansing extends MobEffect {
    public PotionCleansing() {
        super(MobEffectCategory.BENEFICIAL, 0X21C0DC);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        for (MobEffectInstance effect : entityLivingBaseIn.getActiveEffects()) {
            if (effect.getEffect().value().getCategory() != MobEffectCategory.BENEFICIAL) {
                entityLivingBaseIn.removeEffect(effect.getEffect());
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
