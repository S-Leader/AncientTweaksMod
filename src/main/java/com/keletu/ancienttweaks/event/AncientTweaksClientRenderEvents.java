package com.keletu.ancienttweaks.event;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.baubles.client.*;
import com.keletu.ancienttweaks.cap.TanookiData;
import com.keletu.ancienttweaks.init.ATAttachments;
import com.keletu.ancienttweaks.item.TanookiTailLayer;
import com.keletu.ancienttweaks.packet.TanookiJumpPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = AncientTweaks.MODID, value = Dist.CLIENT)
public final class AncientTweaksClientRenderEvents {

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModelBubbleShield.LAYER_LOCATION, ModelBubbleShield::createBodyLayer);

        event.registerLayerDefinition(ModelAbsorber.LAYER_LOCATION, ModelAbsorber::createBodyLayer);
    }

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        for (PlayerSkin.Model skinName : event.getSkins()) {
            PlayerRenderer renderer = event.getSkin(skinName);

            if (renderer == null) {
                continue;
            }

            ModelBubbleShield spongeShield = new ModelBubbleShield(event.getEntityModels().bakeLayer(ModelBubbleShield.LAYER_LOCATION));

            renderer.addLayer(new LayerBubbleShield(renderer, spongeShield));

            ModelAbsorber absorberModel = new ModelAbsorber(event.getEntityModels().bakeLayer(ModelAbsorber.LAYER_LOCATION));

            renderer.addLayer(new LayerTheAbsorber(renderer, absorberModel));

            renderer.addLayer(new LayerSlimeHead(renderer));
            renderer.addLayer(new LayerShieldBack(renderer));
            renderer.addLayer(new LayerCrabGlove(renderer));
            renderer.addLayer(new TanookiTailLayer<>(renderer, Minecraft.getInstance().getEntityModels()));
        }
    }


    private static boolean wasJumpKeyDown = false;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        TanookiData data = mc.player.getData(ATAttachments.DATA_TYPE);
        boolean isJumpKeyDown = mc.options.keyJump.isDown();

        if (isJumpKeyDown && !wasJumpKeyDown) {
            if (data.isPoweredUp && !mc.player.onGround()) {
                mc.player.jumpFromGround();
                PacketDistributor.sendToServer(new TanookiJumpPayload());
            }
        }

        wasJumpKeyDown = isJumpKeyDown;
    }
}
