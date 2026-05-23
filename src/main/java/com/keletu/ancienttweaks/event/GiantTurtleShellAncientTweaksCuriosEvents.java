package com.keletu.ancienttweaks.event;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.AncientTweaksConfig;
import com.keletu.ancienttweaks.init.ATItems;
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

@EventBusSubscriber(modid = AncientTweaks.MODID)
public class GiantTurtleShellAncientTweaksCuriosEvents {

    private static final Map<UUID, Long> GIANT_TURTLE_SHELL_LAST_HURT_TIME = new HashMap<>();

    @SubscribeEvent
    public static void onLivingHurtByGiantTurtleShell(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (player.level().isClientSide) {
            return;
        }

        if (isGiantTurtleShellEquipped(player) && !event.isCanceled()) {
            GIANT_TURTLE_SHELL_LAST_HURT_TIME.put(player.getUUID(), player.level().getGameTime());
        }
    }

    @SubscribeEvent
    public static void onPlayerTickGiantTurtleShell(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide) {
            return;
        }

        boolean equipped = isGiantTurtleShellEquipped(player);

        if (!equipped) {
            removeGiantTurtleShellModifiers(player);
            GIANT_TURTLE_SHELL_LAST_HURT_TIME.remove(player.getUUID());
            return;
        }

        boolean weakened = false;

        Long lastHurt = GIANT_TURTLE_SHELL_LAST_HURT_TIME.get(player.getUUID());

        int debuffDuration = AncientTweaksConfig.CONFIG_CALAMITY.giantTurtleShellWeaknessTime.get() * 20;

        if (lastHurt != null) {
            long timeSinceHurt = player.level().getGameTime() - lastHurt;

            weakened = timeSinceHurt <= debuffDuration;

            if (timeSinceHurt > debuffDuration) {
                GIANT_TURTLE_SHELL_LAST_HURT_TIME.remove(player.getUUID());
            }
        }

        removeGiantTurtleShellModifiers(player);
        applyGiantTurtleShellModifiers(player, weakened);
    }

    private static boolean isGiantTurtleShellEquipped(Player player) {
        Item giantTurtleShell = ATItems.giantTurtleShell.get();

        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();

        if (mainHand.is(giantTurtleShell) || offHand.is(giantTurtleShell)) {
            return true;
        }

        return CuriosApi.getCuriosInventory(player).map(handler -> handler.findFirstCurio(giantTurtleShell).isPresent()).orElse(false);
    }

    private static void applyGiantTurtleShellModifiers(Player player, boolean weakened) {
        float speedAmount = player.isSprinting() && !weakened ? -AncientTweaksConfig.CONFIG_CALAMITY.giantTurtleShellDashSlowness.get().floatValue() : 0.0F;

        float armorAmount = weakened ? AncientTweaksConfig.CONFIG_CALAMITY.giantTurtleShellWeaknessArmor.get().floatValue() : AncientTweaksConfig.CONFIG_CALAMITY.giantTurtleShellArmor.get().floatValue();

        AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);

        if (speedAttribute != null && speedAmount != 0.0F) {
            speedAttribute.addTransientModifier(new AttributeModifier(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "giant_turtle_shell_movement_speed"), speedAmount, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }

        AttributeInstance armorAttribute = player.getAttribute(Attributes.ARMOR);

        if (armorAttribute != null) {
            armorAttribute.addTransientModifier(new AttributeModifier(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "giant_turtle_shell_armor"), armorAmount, AttributeModifier.Operation.ADD_VALUE));
        }

        AttributeInstance knockbackAttribute = player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);

        if (knockbackAttribute != null) {
            knockbackAttribute.addTransientModifier(new AttributeModifier(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "giant_turtle_shell_knockback_resistance"), 1.0F, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    private static void removeGiantTurtleShellModifiers(Player player) {
        AttributeInstance armorAttribute = player.getAttribute(Attributes.ARMOR);

        if (armorAttribute != null) {
            armorAttribute.removeModifier(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "giant_turtle_shell_armor"));
        }

        AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);

        if (speedAttribute != null) {
            speedAttribute.removeModifier(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "giant_turtle_shell_movement_speed"));
        }

        AttributeInstance knockbackAttribute = player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);

        if (knockbackAttribute != null) {
            knockbackAttribute.removeModifier(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "giant_turtle_shell_knockback_resistance"));
        }
    }
}
