package com.keletu.ancienttweaks.event;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.cap.TanookiData;
import com.keletu.ancienttweaks.init.ATAttachments;
import com.keletu.ancienttweaks.item.TanookiArmor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

    // 处理疾跑蓄力逻辑（服务端和客户端同时运行，避免视角卡顿）
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        TanookiData data = player.getData(ATAttachments.DATA_TYPE);

        // 如果没穿装备，立即重置并退出
        if (!hasArmorEquipped(player)) {
            data.sprintTicks = 0;
            data.isPoweredUp = false;
            return;
        }

        // 蓄力条件：必须在地面上
        if (player.onGround()) {
            if (player.isSprinting()) {
                data.sprintTicks++;
                // 3秒 = 60 Tick，满足条件激活技能，玩家可以起跳
                if (data.sprintTicks >= 60) {
                    data.isPoweredUp = true;
                }
            } else {
                // 如果在地面且停止了疾跑，立刻重置状态
                data.sprintTicks = 0;
                data.isPoweredUp = false;
            }
        }

        if (data.isPoweredUp && player.tickCount % 5 == 0)
            player.level().playSound(null, player.blockPosition(), SoundEvents.NOTE_BLOCK_HARP.value(), SoundSource.PLAYERS, 0.3F, 1.0F);
    }
}
