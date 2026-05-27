package com.keletu.ancienttweaks.event;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.packet.TanookiRolling;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = AncientTweaks.MODID, value = Dist.CLIENT)
public class TanookiSpinClient {

    public static final KeyMapping SPIN_KEY = new KeyMapping("key.ancienttweaks.spin", GLFW.GLFW_KEY_V, "key.categories.ancienttweaks");

    private static int spinTicks = 0; // 记录旋转剩余时间
    private static final int SPIN_DURATION = 8; // 旋转总耗时(tick)，8tick大约0.4秒，表现为快速横扫

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (SPIN_KEY.consumeClick()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && spinTicks == 0 && mc.player.getVehicle() == null && mc.player.getDeltaMovement().horizontalDistance() <= 0.01F && TanookiEvents.hasArmorEquipped(mc.player)) { // 防止连续按键导致不间断旋转

                PacketDistributor.sendToServer(new TanookiRolling());

                // 2. 开启客户端本地的旋转动画
                spinTicks = SPIN_DURATION;
            }
        }
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        if (spinTicks > 0) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && mc.player.getVehicle() == null && mc.player.getDeltaMovement().horizontalDistance() <= 0.01F && TanookiEvents.hasArmorEquipped(mc.player)) {
                // 每次 tick 旋转角度 (360度 / 总耗时)
                float rotationPerTick = 300.0F;

                // turn() 会同时旋转视角和身体
                mc.player.turn(rotationPerTick, 0);

                spinTicks--;
            }
        }
    }
}
