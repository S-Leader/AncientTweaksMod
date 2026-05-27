package com.keletu.ancienttweaks.event;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.cap.TanookiData;
import com.keletu.ancienttweaks.init.ATAttachments;
import com.keletu.ancienttweaks.item.TanookiArmor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = AncientTweaks.MODID)
public class TanookiEvents {

    public static boolean hasArmorEquipped(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof TanookiArmor && player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof TanookiArmor && player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof TanookiArmor && player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof TanookiArmor;
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        TanookiData data = player.getData(ATAttachments.DATA_TYPE);

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