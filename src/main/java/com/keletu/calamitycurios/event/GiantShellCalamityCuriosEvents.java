package com.keletu.calamitycurios.event;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.CalamityCuriosConfig;
import com.keletu.calamitycurios.init.CCItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = CalamityCurios.MODID)
public class GiantShellCalamityCuriosEvents {

    private static final Map<UUID, Long> LAST_HURT_TIME = new HashMap<>();

    @SubscribeEvent
    public static void onLivingHurtByGiantShell(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (player.level().isClientSide) {
            return;
        }

        if (isGiantShellEquipped(player) && !event.isCanceled()) {
            LAST_HURT_TIME.put(player.getUUID(), player.level().getGameTime());
        }
    }

    @SubscribeEvent
    public static void onPlayerTickGiantShell(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide) {
            return;
        }

        boolean equipped = isGiantShellEquipped(player);

        if (!equipped) {
            removeGiantShellModifiers(player);
            LAST_HURT_TIME.remove(player.getUUID());
            return;
        }

        boolean weakened = false;

        Long lastHurt = LAST_HURT_TIME.get(player.getUUID());

        int debuffDuration = CalamityCuriosConfig.CONFIG_CALAMITY.giantShellWeaknessTime.get() * 20;

        if (lastHurt != null) {
            long timeSinceHurt = player.level().getGameTime() - lastHurt;

            weakened = timeSinceHurt <= debuffDuration;

            if (timeSinceHurt > debuffDuration) {
                LAST_HURT_TIME.remove(player.getUUID());
            }
        }

        removeGiantShellModifiers(player);
        applyGiantShellModifiers(player, weakened);
    }

    private static boolean isGiantShellEquipped(Player player) {
        Item giantShell = CCItems.GIANTSHELL.get();

        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();

        if (mainHand.is(giantShell) || offHand.is(giantShell)) {
            return true;
        }

        return CuriosApi.getCuriosInventory(player).map(handler -> handler.findFirstCurio(giantShell).isPresent()).orElse(false);
    }

    private static void applyGiantShellModifiers(Player player, boolean weakened) {
        float speedAmount = player.isSprinting() && !weakened ? -CalamityCuriosConfig.CONFIG_CALAMITY.giantShellDashSlowness.get().floatValue() : 0.0F;

        float armorAmount = weakened ? CalamityCuriosConfig.CONFIG_CALAMITY.giantShellWeaknessArmor.get().floatValue() : CalamityCuriosConfig.CONFIG_CALAMITY.giantShellArmor.get().floatValue();

        AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);

        if (speedAttribute != null && speedAmount != 0.0F) {
            speedAttribute.addTransientModifier(new AttributeModifier(ResourceLocation.fromNamespaceAndPath(CalamityCurios.MODID, "movement_speed"), speedAmount, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }

        AttributeInstance armorAttribute = player.getAttribute(Attributes.ARMOR);

        if (armorAttribute != null) {
            armorAttribute.addTransientModifier(new AttributeModifier(ResourceLocation.fromNamespaceAndPath(CalamityCurios.MODID, "armor"), armorAmount, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    private static void removeGiantShellModifiers(Player player) {
        AttributeInstance armorAttribute = player.getAttribute(Attributes.ARMOR);

        if (armorAttribute != null) {
            armorAttribute.removeModifier(ResourceLocation.fromNamespaceAndPath(CalamityCurios.MODID, "armor"));
        }

        AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);

        if (speedAttribute != null) {
            speedAttribute.removeModifier(ResourceLocation.fromNamespaceAndPath(CalamityCurios.MODID, "movement_speed"));
        }
    }
}