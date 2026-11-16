package com.keletu.calamitycurios.baubles.client;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.init.CCItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import top.theillusivec4.curios.api.CuriosApi;

public final class LayerMechanicalHeart extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(CalamityCurios.MODID, "mechanical_heart"), "main");
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(CalamityCurios.MODID, "textures/layer/mechanical_heart_outlayer.png");

    private final HumanoidModel<AbstractClientPlayer> model;

    public LayerMechanicalHeart(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent, HumanoidModel<AbstractClientPlayer> model) {
        super(parent);
        this.model = model;
    }

    public static LayerDefinition createBodyLayer() {
        return LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.35F), 0.0F), 64, 64);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffers, int packedLight, AbstractClientPlayer player,
                       float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (player.isInvisible() || player.isSpectator()) {
            return;
        }
        boolean visible = CuriosApi.getCuriosInventory(player)
                .map(handler -> handler.findCurios(CCItems.MECHANICAL_HEART.get()).stream()
                        .anyMatch(result -> result.slotContext().visible()))
                .orElse(false);
        if (!visible) {
            return;
        }
        getParentModel().copyPropertiesTo(model);
        model.setAllVisible(true);
        model.hat.visible = false;
        model.renderToBuffer(poseStack, buffers.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY);
    }
}
