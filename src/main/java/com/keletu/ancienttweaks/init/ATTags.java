package com.keletu.ancienttweaks.init;

import com.keletu.ancienttweaks.AncientTweaks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ATTags extends TagBuilder {
    public static final TagKey<Item> TANOOKI_MAT = bind("tanooki_mat");
    public static final TagKey<Item> TANOOKI_CORE = bind("tanooki_core");
    public static final TagKey<Item> THUNDER_MAT = bind("thunder_mat");
    public static final TagKey<Item> THUNDER_CORE = bind("thunder_core");

    private static TagKey<Item> bind(String name) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, name));
    }
}