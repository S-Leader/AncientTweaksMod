package com.keletu.ancienttweaks.event;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.baubles.soulheart.SoulHeartClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(modid = AncientTweaks.MODID, value = Dist.CLIENT)
public final class AncientTweaksClientEvents {

    private AncientTweaksClientEvents() {
    }

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null) {
            return;
        }

        event.getGuiGraphics().pose().pushPose();
        event.getGuiGraphics().pose().translate(0, 0, 200);

        SoulHeartClientHandler.renderHUD(event.getGuiGraphics(), player, null);

        event.getGuiGraphics().pose().popPose();
    }
}
