package com.keletu.ancienttweaks.item;

import com.keletu.ancienttweaks.AncientTweaks;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class TanookiArmor extends ArmorItem {
    public TanookiArmor(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "textures/models/armor/tanooki_layer_" + (slot == EquipmentSlot.LEGS ? "2" : "1") + ".png");
    }
}
