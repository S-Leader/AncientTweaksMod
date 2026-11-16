package com.keletu.calamitycurios.init;

import com.keletu.calamitycurios.CalamityCurios;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class CCArmorMats {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = net.neoforged.neoforge.registries.DeferredRegister.create(Registries.ARMOR_MATERIAL, CalamityCurios.MODID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> TANOOKI = ARMOR_MATERIALS.register("tanooki", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 3);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.HELMET, 3);
    }), 9, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(ModList.get().isLoaded("twilightforest") ? CCTags.TANOOKI_MAT : Tags.Items.INGOTS_GOLD), List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(CalamityCurios.MODID, "tanooki"))), 0.0F, 0.0F));

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> THUNDER = ARMOR_MATERIALS.register("thunder", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.LEGGINGS, 5);
        map.put(ArmorItem.Type.CHESTPLATE, 6);
        map.put(ArmorItem.Type.HELMET, 2);
    }), 9, SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(ModList.get().isLoaded("aether_genesis") ? CCTags.THUNDER_MAT : Tags.Items.INGOTS_IRON), List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(CalamityCurios.MODID, "thunder"))), 2, 0.0F));
}
