package com.keletu.ancienttweaks.event;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.item.ThunderArmor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = AncientTweaks.MODID)
public class ThunderEvents {
    public static boolean hasArmorEquipped(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ThunderArmor && player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ThunderArmor && player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ThunderArmor && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ThunderArmor;
    }

    private static void applyFullBuff(Player player) {
        float speedAmount = player.isSprinting() ? 1.25F : 1.15F;

        AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);

        if (speedAttribute != null && speedAmount != 0.0F) {
            speedAttribute.addTransientModifier(new AttributeModifier(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "thunder_armor_movement_speed"), speedAmount, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }

        AttributeInstance jumpModifier = player.getAttribute(Attributes.JUMP_STRENGTH);

        if (jumpModifier != null) {
            jumpModifier.addTransientModifier(new AttributeModifier(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "thunder_armor_jump_height"), 0.2F, AttributeModifier.Operation.ADD_VALUE));
        }

        AttributeInstance landModifier = player.getAttribute(Attributes.SAFE_FALL_DISTANCE);

        if (landModifier != null) {
            landModifier.addTransientModifier(new AttributeModifier(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "thunder_armor_fall_protection"), 2.0F, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    private static void removeModifier(Player player) {
        AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);

        if (speedAttribute != null) {
            speedAttribute.removeModifier(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "thunder_armor_movement_speed"));
        }

        AttributeInstance jumpModifier = player.getAttribute(Attributes.JUMP_STRENGTH);

        if (jumpModifier != null) {
            jumpModifier.removeModifier(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "thunder_armor_jump_height"));
        }

        AttributeInstance landModifier = player.getAttribute(Attributes.SAFE_FALL_DISTANCE);

        if (landModifier != null) {
            landModifier.removeModifier(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "thunder_armor_fall_protection"));
        }
    }

    @SubscribeEvent
    public static void onPlayerTickGiantTurtleShell(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide) {
            return;
        }

        boolean equipped = hasArmorEquipped(player);

        if (!equipped) {
            removeModifier(player);
            return;
        }


        removeModifier(player);
        applyFullBuff(player);
    }

    @SubscribeEvent
    public static void onPlayerRangedAttack(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player attacker && event.getAmount() < Float.MAX_VALUE / 2) {
            if (event.getSource().getDirectEntity() != attacker && !attacker.level().isClientSide && hasArmorEquipped(attacker)) {
                float amount = event.getAmount();
                amount *= 1.5F;
                if (attacker.getRandom().nextInt(100) < 15) {
                    amount *= 2;
                    LightningBolt blot = new LightningBolt(EntityType.LIGHTNING_BOLT, attacker.level());
                    blot.setVisualOnly(true);
                    blot.setPos(event.getEntity().position());
                    attacker.level().addFreshEntity(blot);
                }

                event.setAmount(amount);
            }

        }
    }
}