package com.keletu.ancienttweaks.mixins;

import com.keletu.ancienttweaks.init.ATEffects;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class MixinDamageCalculations {

    @WrapOperation(
            method = "getDamageAfterMagicAbsorb",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getDamageProtection(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/damagesource/DamageSource;)F"
            )
    )
    private float wrapGetDamageProtection(ServerLevel level, LivingEntity entity, DamageSource damageSource, Operation<Float> original) {
        float lvl = original.call(level, entity, damageSource);

        LivingEntity self = (LivingEntity) (Object) this;
        MobEffectInstance effect = self.getEffect(ATEffects.CRUMBLING);

        if (effect != null) {
            lvl -= (effect.getAmplifier() + 1) * 3;
        }

        return Math.max(0, lvl);
    }
}