package com.keletu.ancienttweaks.event;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.init.ATAttachments;
import com.keletu.ancienttweaks.packet.TanookiRolling;
import com.keletu.ancienttweaks.packet.TanookiStatuePayload;
import com.keletu.ancienttweaks.packet.TanookiStatueTimeServerEvent;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = AncientTweaks.MODID, value = Dist.CLIENT)
public class TanookiSpinClient {

    public static final KeyMapping SPIN_KEY = new KeyMapping("key.ancienttweaks.spin", GLFW.GLFW_KEY_V, "key.categories.ancienttweaks");

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.player.getVehicle() != null) return;

        var data = mc.player.getData(ATAttachments.DATA_TYPE);

        boolean isKeyDown = SPIN_KEY.isDown();
        boolean inAir = !mc.player.onGround();

        int wasStatueThisTick = data.statueTime;

        if (isKeyDown && inAir && data.statueTime == 0) {
            data.statueTime = 100;
        } else if (data.statueTime == 0 && !inAir) {
            PacketDistributor.sendToServer(new TanookiStatuePayload(false));
        }

        if (data.statueTime > 0) {
            data.statueTime--;
            PacketDistributor.sendToServer(new TanookiStatuePayload(true));
            mc.player.setDeltaMovement(0, -1.5, 0);
        }

        PacketDistributor.sendToServer(new TanookiStatueTimeServerEvent(data.statueTime));

        while (SPIN_KEY.consumeClick()) {
            if (wasStatueThisTick > 0) {
                continue;
            }

            if (!inAir && data.spinTicks == 0) {
                boolean isStationary = mc.player.getDeltaMovement().horizontalDistanceSqr() < 0.001;
                if (isStationary) {
                    data.spinTicks = 8;
                    PacketDistributor.sendToServer(new TanookiRolling());
                }
            }
        }

        if (data.spinTicks > 0) {
            mc.player.turn(300, 0);
            data.spinTicks--;
        }
    }
}
