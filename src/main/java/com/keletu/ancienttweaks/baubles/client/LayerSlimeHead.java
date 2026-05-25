package com.keletu.ancienttweaks.baubles.client;

import com.keletu.ancienttweaks.init.ATItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Optional;

public class LayerSlimeHead extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public LayerSlimeHead(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        poseStack.pushPose();

        if (this.getParentModel().young) {
            poseStack.translate(0.0F, 0.75F, 0.0F);
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }

        Optional<ItemStack> blueJelly = findCurio(player, ATItems.blueJelly.get());
        Optional<ItemStack> greenJelly = findCurio(player, ATItems.greenJelly.get());
        Optional<ItemStack> pinkJelly = findCurio(player, ATItems.pinkJelly.get());

        if (blueJelly.isPresent()) {
            this.renderHeadItem(player, blueJelly.get(), poseStack, buffer, packedLight);
        } else if (greenJelly.isPresent()) {
            this.renderHeadItem(player, greenJelly.get(), poseStack, buffer, packedLight);
        } else if (pinkJelly.isPresent()) {
            this.renderHeadItem(player, pinkJelly.get(), poseStack, buffer, packedLight);
        }

        poseStack.popPose();
    }

    private void renderHeadItem(AbstractClientPlayer player, ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (stack.isEmpty()) {
            return;
        }

        poseStack.pushPose();

        if (player.isShiftKeyDown()) {
            poseStack.translate(0.0F, 0.2F, 0.0F);
        }

        this.translateToHead(poseStack);

        //poseStack.scale(1.25F, 1.25F, 1.25F);
        //poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        //poseStack.translate(0.0F, 0.2F, 0.0F);

        Minecraft.getInstance().getItemRenderer().renderStatic(player, stack, ItemDisplayContext.HEAD, false, poseStack, buffer, player.level(), packedLight, OverlayTexture.NO_OVERLAY, player.getId());

        poseStack.popPose();
    }

    protected void translateToHead(PoseStack poseStack) {
        this.getParentModel().head.translateAndRotate(poseStack);
    }

    private static Optional<ItemStack> findCurio(AbstractClientPlayer player, Item item) {
        return CuriosApi.getCuriosInventory(player).flatMap(handler -> handler.findFirstCurio(item)).map(slotResult -> slotResult.stack());
    }
}