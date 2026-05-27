package com.keletu.ancienttweaks.item;

import com.keletu.ancienttweaks.AncientTweaks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;

import javax.annotation.Nullable;
import java.util.List;

public class TanookiArmor extends ArmorItem {
    public TanookiArmor(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "textures/models/armor/tanooki_layer_" + (slot == EquipmentSlot.LEGS ? "2" : "1") + ".png");
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Item.TooltipContext level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.empty());

        tooltip.add(Component.translatable("tooltip.ancienttweaks.tanooki_armor1").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.ancienttweaks.tanooki_armor2").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.ancienttweaks.tanooki_armor3").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.ancienttweaks.tanooki_armor4").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.ancienttweaks.tanooki_armor5").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.ancienttweaks.tanooki_armor6").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GOLD));
    }
}
