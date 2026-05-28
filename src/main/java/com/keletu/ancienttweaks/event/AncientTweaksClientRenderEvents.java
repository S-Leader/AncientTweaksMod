package com.keletu.ancienttweaks.event;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.baubles.client.*;
import com.keletu.ancienttweaks.baubles.soulheart.SoulHeartClientHandler;
import com.keletu.ancienttweaks.cap.TanookiData;
import com.keletu.ancienttweaks.init.ATAttachments;
import com.keletu.ancienttweaks.item.StatueLayer;
import com.keletu.ancienttweaks.item.StatueModel;
import com.keletu.ancienttweaks.item.TanookiModel;
import com.keletu.ancienttweaks.item.TanookiTailLayer;
import com.keletu.ancienttweaks.packet.TanookiJumpPayload;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = AncientTweaks.MODID, value = Dist.CLIENT)
public final class AncientTweaksClientRenderEvents {

    private static final ResourceLocation STATUE_TEXTURE = ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "textures/models/statue_steve.png");

    private static StatueModel getModel() {
        return new StatueModel(Minecraft.getInstance().getEntityModels().bakeLayer(StatueModel.LAYER_LOCATION));
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModelBubbleShield.LAYER_LOCATION, ModelBubbleShield::createBodyLayer);
        event.registerLayerDefinition(TanookiModel.LAYER_LOCATION, TanookiModel::createBodyLayer);
        event.registerLayerDefinition(StatueModel.LAYER_LOCATION, StatueModel::createBodyLayer);
        event.registerLayerDefinition(ModelAbsorber.LAYER_LOCATION, ModelAbsorber::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();
        var data = player.getData(ATAttachments.DATA_TYPE);

        if (data == null || data.statueTime <= 0 || !TanookiEvents.hasArmorEquipped(player)) {
            return;
        }

        event.setCanceled(true);

        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource buffer = event.getMultiBufferSource();
        int packedLight = event.getPackedLight();

        StatueModel model = getModel();

        poseStack.pushPose();

        float bodyYaw = player.yBodyRotO + (player.yBodyRot - player.yBodyRotO) * event.getPartialTick();
        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(180.0F - bodyYaw));

        poseStack.translate(0, 1.5D, 0);
        poseStack.scale(1.0F, -1.0F, 1.0F);

        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(STATUE_TEXTURE));
        model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
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
            renderer.addLayer(new StatueLayer<>(renderer, Minecraft.getInstance().getEntityModels()));
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
                PacketDistributor.sendToServer(new TanookiJumpPayload());
            }
        }

        wasJumpKeyDown = isJumpKeyDown;
    }

    @SubscribeEvent
    public static void onRenderGuiLayer(RenderGuiLayerEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null) {
            return;
        }

        SoulHeartClientHandler.renderHUD(event.getGuiGraphics(), player, null);
    }
}
