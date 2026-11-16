package com.keletu.calamitycurios.item;

import com.keletu.calamitycurios.CalamityCurios;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.Nullable;
import java.util.List;

public class ThunderArmor extends ArmorItem {
    public ThunderArmor(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return ResourceLocation.fromNamespaceAndPath(CalamityCurios.MODID, "textures/models/armor/thunder_layer_" + (slot == EquipmentSlot.LEGS ? "2" : "1") + ".png");
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable TooltipContext level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.empty());

        tooltip.add(Component.translatable("tooltip.calamitycurios.thunder_armor1").withStyle(ChatFormatting.LIGHT_PURPLE));
        tooltip.add(Component.translatable("tooltip.calamitycurios.thunder_armor2").withStyle(ChatFormatting.LIGHT_PURPLE));
        tooltip.add(Component.translatable("tooltip.calamitycurios.thunder_armor3").withStyle(ChatFormatting.LIGHT_PURPLE));
        tooltip.add(Component.translatable("tooltip.calamitycurios.thunder_armor4").withStyle(ChatFormatting.LIGHT_PURPLE));
        tooltip.add(Component.translatable("tooltip.calamitycurios.thunder_armor5").withStyle(ChatFormatting.LIGHT_PURPLE));
        tooltip.add(Component.translatable("tooltip.calamitycurios.thunder_armor6").withStyle(ChatFormatting.LIGHT_PURPLE));
    }
}
