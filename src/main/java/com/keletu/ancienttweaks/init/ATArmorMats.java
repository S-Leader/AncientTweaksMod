package com.keletu.ancienttweaks.init;

import com.keletu.ancienttweaks.AncientTweaks;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class ATArmorMats {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = net.neoforged.neoforge.registries.DeferredRegister.create(Registries.ARMOR_MATERIAL, AncientTweaks.MODID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> TANOOKI = ARMOR_MATERIALS.register("tanooki", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.HELMET, 3);
    }), 9, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Tags.Items.INGOTS_GOLD), List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "tanooki"))), 20, 0.0F));
}
