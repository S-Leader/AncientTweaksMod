package com.keletu.calamitycurios.event;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.CalamityCuriosConfig;
import com.keletu.calamitycurios.cap.HurtCounter;
import com.keletu.calamitycurios.init.CCAttachments;
import com.keletu.calamitycurios.init.CCEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = CalamityCurios.MODID)
public class ArmorBreakEvent {

    @SubscribeEvent
    public static void onCrumblingRemoved(MobEffectEvent.Remove event) {
        if (!CalamityCuriosConfig.ENABLE_ARMOR_BREAK_TWEAKS.get()) return;

        if (event.getEffect().value() == CCEffects.CRUMBLING.get()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingIncomingDamageEvent event) {
        if (!CalamityCuriosConfig.ENABLE_ARMOR_BREAK_TWEAKS.get()) return;

        if (event.getAmount() >= Float.MAX_VALUE) {
            return;
        }

        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        HurtCounter hurtCounter = player.getData(CCAttachments.HURT_COUNTER);

        LivingEntity attacker = null;
        if (event.getSource().getEntity() instanceof LivingEntity living) {
            attacker = living;
        }

        if (event.getAmount() > 0 && attacker != null) {
            double amount = event.getAmount();

            if (event.getSource().getEntity().getType().is(Tags.EntityTypes.BOSSES)) {
                hurtCounter.setHurtCounter(Math.min(hurtCounter.getHurtCounter() + amount, 1000));
            } else {
                hurtCounter.setHurtCounter(Math.min(hurtCounter.getHurtCounter() + amount / 2.0D, 1000));
            }

            hurtCounter.setHurtSinceLastDamage(CalamityCuriosConfig.CONFIG_CRUMBLING.fadeTimer.get());
        }
    }

    @SubscribeEvent
    public static void tickHandler(PlayerTickEvent.Post event) {
        if (!CalamityCuriosConfig.ENABLE_ARMOR_BREAK_TWEAKS.get()) return;
        Player player = event.getEntity();

        if (player.level().isClientSide) {
            return;
        }

        if (player.tickCount % 20 != 0) {
            return;
        }

        HurtCounter hurtCounter = player.getData(CCAttachments.HURT_COUNTER);

        if (hurtCounter.getHurtCounter() > 0) {
            if (hurtCounter.getHurtSinceLastDamage() == 0) {
                hurtCounter.setHurtCounter(hurtCounter.getHurtCounter() - Math.min(1.0D, hurtCounter.getHurtCounter()));
            }

            if (hurtCounter.getHurtCounter() >= CalamityCuriosConfig.CONFIG_CRUMBLING.level1Damage.get()) {
                int amplifier = hurtCounter.getHurtCounter() >= CalamityCuriosConfig.CONFIG_CRUMBLING.level2Damage.getAsDouble() ? hurtCounter.getHurtCounter() >= CalamityCuriosConfig.CONFIG_CRUMBLING.level3Damage.getAsDouble() ? hurtCounter.getHurtCounter() >= CalamityCuriosConfig.CONFIG_CRUMBLING.level4Damage.getAsDouble() ? hurtCounter.getHurtCounter() >= CalamityCuriosConfig.CONFIG_CRUMBLING.level5Damage.getAsDouble() ? 4 : 3 : 2 : 1 : 0;

                player.addEffect(new MobEffectInstance(CCEffects.CRUMBLING, 60, amplifier, false, false, true));
            }
        }

        if (hurtCounter.getHurtSinceLastDamage() > 0) {
            hurtCounter.setHurtSinceLastDamage(hurtCounter.getHurtSinceLastDamage() - 1);
        }
    }
}
