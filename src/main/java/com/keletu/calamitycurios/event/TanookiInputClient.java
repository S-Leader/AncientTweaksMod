package com.keletu.calamitycurios.event;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.packet.TanookiInputPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = CalamityCurios.MODID, value = Dist.CLIENT)
public class TanookiInputClient {

    private static boolean lastKeyDown = false;

    @SubscribeEvent
    public static void onClientTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (!player.level().isClientSide()) return;
        if (Minecraft.getInstance().player != player) return;
        if (player.getVehicle() != null) return;
        if (!TanookiEvents.hasArmorEquipped(player)) return;

        boolean keyDown = ClientEvents.SPIN_KEY.isDown();

        if (keyDown != lastKeyDown) {
            PacketDistributor.sendToServer(new TanookiInputPayload(keyDown, false));
            lastKeyDown = keyDown;
        }

        while (ClientEvents.SPIN_KEY.consumeClick()) {
            PacketDistributor.sendToServer(new TanookiInputPayload(keyDown, true));
        }
    }
}