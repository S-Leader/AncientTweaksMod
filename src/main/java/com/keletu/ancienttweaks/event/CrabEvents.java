package com.keletu.ancienttweaks.event;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.AncientTweaksConfig;
import com.keletu.ancienttweaks.baubles.ItemBaroClaw;
import com.keletu.ancienttweaks.init.ATEffects;
import com.keletu.ancienttweaks.init.ATItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(modid = AncientTweaks.MODID)
public class CrabEvents {


    @SubscribeEvent
    public static void onEntityHurt(LivingIncomingDamageEvent event) {
        if (event.getAmount() >= Float.MAX_VALUE) {
            return;
        }

        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (!ItemBaroClaw.hasBaroClaw(player)) {
            return;
        }

        Entity sourceEntity = event.getSource().getEntity();
        Entity directEntity = event.getSource().getDirectEntity();

        if (!(sourceEntity instanceof LivingEntity attacker)) {
            return;
        }

        if (event.getSource().is(DamageTypes.THORNS)) {
            return;
        }

        if (directEntity != attacker) {
            return;
        }

        float reflectedDamage = event.getAmount() * AncientTweaksConfig.CONFIG_CALAMITY.baroclawReflectDamage.get().floatValue();

        DamageSource thornsDamage = player.damageSources().thorns(player);

        attacker.hurt(thornsDamage, reflectedDamage);

        attacker.addEffect(new MobEffectInstance(ATEffects.CRUMBLING, AncientTweaksConfig.CONFIG_CALAMITY.baroclawArmorWeaknessTime.get() * 20, 0));
    }

    @SubscribeEvent
    public static void onEntityHurtByCrabClaw(LivingIncomingDamageEvent event) {
        if (event.getAmount() >= Float.MAX_VALUE) {
            return;
        }

        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        boolean hasCrabClaw = CuriosApi.getCuriosInventory(player).map(handler -> handler.findFirstCurio(ATItems.CRAWCARAPACE.get()).isPresent()).orElse(false);

        if (!hasCrabClaw) {
            return;
        }

        DamageSource source = event.getSource();

        if (source.is(DamageTypes.THORNS)) {
            return;
        }

        Entity sourceEntity = source.getEntity();
        Entity directEntity = source.getDirectEntity();

        if (!(sourceEntity instanceof LivingEntity attacker)) {
            return;
        }

        if (directEntity != attacker) {
            return;
        }

        float reflectedDamage = event.getAmount() * AncientTweaksConfig.CONFIG_CALAMITY.crabClawReflectDamage.get().floatValue();

        DamageSource thornsDamage = player.damageSources().thorns(player);

        attacker.hurt(thornsDamage, reflectedDamage);

        attacker.addEffect(new MobEffectInstance(ATEffects.CRUMBLING, AncientTweaksConfig.CONFIG_CALAMITY.crabClawArmorWeaknessTime.get() * 20, 0));
    }
}