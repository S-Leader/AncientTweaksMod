package com.keletu.ancienttweaks.event;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.cap.TanookiData;
import com.keletu.ancienttweaks.init.ATAttachments;
import com.keletu.ancienttweaks.item.TanookiArmor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = AncientTweaks.MODID)
public class TanookiEvents {
    private static final ResourceLocation STATUE_MOVE_ID = ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "statue_move_lock");
    private static final ResourceLocation STATUE_JUMP_ID = ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "statue_jump_lock");

    public static boolean hasArmorEquipped(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof TanookiArmor && player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof TanookiArmor && player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof TanookiArmor && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof TanookiArmor;
    }

    @SubscribeEvent
    public static void onPlayerAttack(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            if (player.getData(ATAttachments.DATA_TYPE).statueTime > 0) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if (event.getEntity() instanceof Player player && player.getData(ATAttachments.DATA_TYPE).statueTime > 0) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntity() instanceof Player player && player.getData(ATAttachments.DATA_TYPE).statueTime > 0) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (event.getEntity() instanceof Player player && player.getData(ATAttachments.DATA_TYPE).statueTime > 0) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        if (event.getEntity() instanceof Player player && player.getData(ATAttachments.DATA_TYPE).statueTime > 0) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onInteractEntity(PlayerInteractEvent.EntityInteract event) {
        if (event.getEntity() instanceof Player player && player.getData(ATAttachments.DATA_TYPE).statueTime > 0) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onInteractEntitySpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        if (event.getEntity() instanceof Player player && player.getData(ATAttachments.DATA_TYPE).statueTime > 0) {
            event.setCanceled(true);
        }
    }

    private static void applyStatueModifiers(Player player) {
        var movement = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movement != null && movement.getModifier(STATUE_MOVE_ID) == null) {
            movement.addPermanentModifier(new AttributeModifier(STATUE_MOVE_ID, -10.0D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }

        var jumpHeight = player.getAttribute(Attributes.JUMP_STRENGTH);
        if (jumpHeight != null && jumpHeight.getModifier(STATUE_JUMP_ID) == null) {
            jumpHeight.addPermanentModifier(new AttributeModifier(STATUE_JUMP_ID, 0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
    }

    private static void removeStatueModifiers(Player player) {
        var movement = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movement != null) {
            movement.removeModifier(STATUE_MOVE_ID);
        }

        var jumpHeight = player.getAttribute(Attributes.JUMP_STRENGTH);
        if (jumpHeight != null) {
            jumpHeight.removeModifier(STATUE_JUMP_ID);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        TanookiData data = player.getData(ATAttachments.DATA_TYPE);

        if (data.statueTime > 0) {
            applyStatueModifiers(player);
        } else {
            removeStatueModifiers(player);
        }


        if (!hasArmorEquipped(player)) {
            data.sprintTicks = 0;
            data.isPoweredUp = false;
            return;
        }

        if (player.onGround()) {
            if (player.isSprinting()) {
                data.sprintTicks++;
                if (data.sprintTicks >= 60) {
                    data.isPoweredUp = true;
                }
            } else {
                data.sprintTicks = 0;
                data.isPoweredUp = false;
            }
        }

        if (data.isPoweredUp && player.tickCount % 5 == 0)
            player.playSound(SoundEvents.NOTE_BLOCK_HARP.value(), 0.3F, 1.0F);
    }
}