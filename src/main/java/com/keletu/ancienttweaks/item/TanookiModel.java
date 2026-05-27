package com.keletu.ancienttweaks.item;// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.keletu.ancienttweaks.AncientTweaks;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;


public class TanookiModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "textures/models/armor/tanooki_layer_2.png"), "main");
    private final ModelPart tail;

    public TanookiModel(ModelPart root) {
        this.tail = root.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 10.5F, 2.25F, 0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r1 = tail.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(48, 18).addBox(-2.0F, -2.0F, -1.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 1.7453F, 0.0F, 0.0f));
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float basePitch = 0.5236F;

        if (!entity.onGround() && !entity.isPassenger()) {
            this.tail.xRot = basePitch - 0.6F;

            this.tail.yRot = Mth.cos(ageInTicks * 1.2F) * 0.6F;

            this.tail.zRot = 0.0F;
        } else {
            float idleWag = Mth.cos(ageInTicks * 0.1F) * 0.05F;
            float idlePitch = Mth.sin(ageInTicks * 0.1F) * 0.05F;

            float moveWag = Mth.cos(limbSwing * 0.6662F) * 0.4F * limbSwingAmount;

            this.tail.xRot = basePitch + idlePitch;
            this.tail.yRot = idleWag + moveWag;
            this.tail.zRot = 0.0F;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        tail.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}