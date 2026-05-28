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
import net.minecraft.world.entity.LivingEntity;

public class StatueModel<T extends LivingEntity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "textures/models/statue_steve.png"), "main");
    private final ModelPart Waist;
    private final ModelPart Head;
    private final ModelPart Body;
    private final ModelPart RightArm;
    private final ModelPart LeftArm;
    private final ModelPart RightLeg;
    private final ModelPart LeftLeg;
    private final ModelPart bb_main;

    public StatueModel(ModelPart root) {
        this.Waist = root.getChild("Waist");
        this.Head = this.Waist.getChild("Head");
        this.Body = this.Waist.getChild("Body");
        this.RightArm = this.Waist.getChild("Right Arm");
        this.LeftArm = this.Waist.getChild("Left Arm");
        this.RightLeg = root.getChild("Right Leg");
        this.LeftLeg = root.getChild("Left Leg");
        this.bb_main = root.getChild("bb_main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Waist = partdefinition.addOrReplaceChild("Waist", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition Head = Waist.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition Body = Waist.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition RightArm = Waist.addOrReplaceChild("Right Arm", CubeListBuilder.create(), PartPose.offset(-5.0F, -10.0F, 0.0F));

        PartDefinition RightArm_r1 = RightArm.addOrReplaceChild("Right Arm_r1", CubeListBuilder.create().texOffs(40, 16).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0F)), PartPose.offsetAndRotation(5.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition LeftArm = Waist.addOrReplaceChild("Left Arm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0F)), PartPose.offset(5.0F, -10.0F, 0.0F));

        PartDefinition RightLeg = partdefinition.addOrReplaceChild("Right Leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

        PartDefinition LeftLeg = partdefinition.addOrReplaceChild("Left Leg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0F)), PartPose.offset(1.9F, 12.0F, 0.0F));

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 32).addBox(-10.0F, -32.0F, -11.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-1.5F)).texOffs(56, 40).addBox(-7.0F, -32.0F, -8.0F, 2.0F, 22.0F, 2.0F, new CubeDeformation(0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        Waist.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        RightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        LeftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}