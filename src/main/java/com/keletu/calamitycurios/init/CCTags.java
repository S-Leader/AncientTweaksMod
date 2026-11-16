package com.keletu.calamitycurios.init;

import com.keletu.calamitycurios.CalamityCurios;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CCTags {

    public static final TagKey<Item> TANOOKI_MAT = bind("tanooki_mat");
    public static final TagKey<Item> TANOOKI_CORE = bind("tanooki_core");
    public static final TagKey<Item> THUNDER_MAT = bind("thunder_mat");
    public static final TagKey<Item> THUNDER_CORE = bind("thunder_core");

    private static TagKey<Item> bind(String name) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(CalamityCurios.MODID, name));
    }
}