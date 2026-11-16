package com.keletu.calamitycurios.event;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.CalamityCuriosConfig;
import com.keletu.calamitycurios.init.CCEffects;
import com.keletu.calamitycurios.init.CCItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(modid = CalamityCurios.MODID)
public class AbsorberCalamityCuriosEvents {

    @SubscribeEvent
    public static void onEntityKnockBack(LivingKnockBackEvent event) {
        LivingEntity living = event.getEntity();

        if (!(living instanceof Player player)) {
            return;
        }

        if (isCurioEquipped(player, CCItems.theAbsorber.get())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onEntityHurtByAbsorber(LivingIncomingDamageEvent event) {
        if (event.getAmount() >= Float.MAX_VALUE) {
            return;
        }

        LivingEntity hurtEntity = event.getEntity();

        if (!(hurtEntity instanceof Player player)) {
            return;
        }

        if (player.level().isClientSide) {
            return;
        }

        if (!isCurioEquipped(player, CCItems.theAbsorber.get())) {
            return;
        }

        if (event.isCanceled()) {
            return;
        }

        Entity directEntity = event.getSource().getDirectEntity();
        Entity sourceEntity = event.getSource().getEntity();

        if (sourceEntity instanceof LivingEntity attacker && directEntity == attacker) {
            if (!event.getSource().is(DamageTypes.THORNS)) {
                float reflectedDamage = Math.max(event.getAmount() * CalamityCuriosConfig.CONFIG_CALAMITY.theAbsorberReflectDamage.get().floatValue(), CalamityCuriosConfig.CONFIG_CALAMITY.theAbsorberReflectDamageMinimum.get().floatValue());

                attacker.hurt(player.damageSources().thorns(player), reflectedDamage);

                attacker.addEffect(new MobEffectInstance(CCEffects.CRUMBLING, CalamityCuriosConfig.CONFIG_CALAMITY.theAbsorberWeaknessTime.get() * 20, 1));

                attacker.addEffect(new MobEffectInstance(MobEffects.WITHER, CalamityCuriosConfig.CONFIG_CALAMITY.theAbsorberWitherTime.get() * 20, CalamityCuriosConfig.CONFIG_CALAMITY.theAbsorberWitherLevel.get() - 1));
            }
        }

        if (event.getAmount() >= 0.0F) {
            float heal = event.getAmount() * CalamityCuriosConfig.CONFIG_CALAMITY.theAbsorberAbsorb.get().floatValue();

            event.setAmount(event.getAmount() * (1.0F - CalamityCuriosConfig.CONFIG_CALAMITY.theAbsorberAbsorb.get().floatValue()));

            player.heal(Math.max(0.0F, heal));
        }
    }

    @SubscribeEvent
    public static void onEntityEatByAbsorber(LivingEntityUseItemEvent.Finish event) {
        LivingEntity living = event.getEntity();

        if (!(living instanceof Player player)) {
            return;
        }

        if (player.level().isClientSide) {
            return;
        }

        ItemStack eatenStack = event.getItem();

        if (!eatenStack.getComponents().has(DataComponents.FOOD)) {
            return;
        }

        if (player.getFoodData().needsFood()) {
            return;
        }

        if (!isCurioEquipped(player, CCItems.theAbsorber.get())) {
            return;
        }

        AreaEffectCloud cloud = createBasePotionCloud(player, 3.5F, 100, -0.03F);

        cloud.addEffect(new MobEffectInstance(MobEffects.REGENERATION, CalamityCuriosConfig.CONFIG_CALAMITY.theAbsorberRegenerationTime.get() * 20, CalamityCuriosConfig.CONFIG_CALAMITY.theAbsorberRegenerationLevel.get() - 1));

        cloud.addEffect(new MobEffectInstance(CCEffects.CLEANSING, 1, 0));

        cloud.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, CalamityCuriosConfig.CONFIG_CALAMITY.theAbsorberStrengthTime.get() * 20, CalamityCuriosConfig.CONFIG_CALAMITY.theAbsorberStrengthLevel.get() - 1));

        cloud.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, CalamityCuriosConfig.CONFIG_CALAMITY.theAbsorberResistanceTime.get() * 20, CalamityCuriosConfig.CONFIG_CALAMITY.theAbsorberResistanceLevel.get() - 1));

        player.level().addFreshEntity(cloud);
    }

    private static boolean isCurioEquipped(Player player, Item item) {
        return CuriosApi.getCuriosInventory(player).map(handler -> handler.findFirstCurio(item).isPresent()).orElse(false);
    }

    private static AreaEffectCloud createBasePotionCloud(Player player, float radius, int duration, float radiusPerTick) {
        AreaEffectCloud cloud = new AreaEffectCloud(
                player.level(),
                player.getX(),
                player.getY(),
                player.getZ()
        );

        cloud.setOwner(player);
        cloud.setRadius(radius);
        cloud.setDuration(duration);
        cloud.setRadiusPerTick(radiusPerTick);

        cloud.setParticle(ColorParticleOption.create(
                ParticleTypes.ENTITY_EFFECT,
                0xFFFFFFFF
        ));

        return cloud;
    }
}