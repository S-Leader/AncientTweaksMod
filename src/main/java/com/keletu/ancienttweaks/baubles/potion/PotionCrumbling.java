package com.keletu.ancienttweaks.baubles.potion;

import com.keletu.ancienttweaks.AncientTweaks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class PotionCrumbling extends MobEffect {
    public PotionCrumbling() {
        super(MobEffectCategory.HARMFUL, 0x464046);
        this.addAttributeModifier(Attributes.ARMOR, ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "crumbling"), -0.15F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
