package com.keletu.calamitycurios.init;

import com.keletu.calamitycurios.CalamityCurios;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class CCTagData extends ItemTagsProvider {
    public CCTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper helper) {
        super(output, registries, blockTags, CalamityCurios.MODID, helper);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("curios", "charm"))).add(CCItems.theCommunity.get(), CCItems.MECHANICAL_HEART.get());
        this.tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("curios", "head"))).add(CCItems.MECHANICAL_HEART.get());
        this.tag(ItemTags.HEAD_ARMOR_ENCHANTABLE).add(CCItems.tanooki_hood.get()).add(CCItems.thunder_helmet.get());
        this.tag(ItemTags.CHEST_ARMOR_ENCHANTABLE).add(CCItems.tanooki_chest.get()).add(CCItems.thunder_chestplate.get());
        this.tag(ItemTags.LEG_ARMOR_ENCHANTABLE).add(CCItems.tanooki_legs.get()).add(CCItems.thunder_leggings.get());
        this.tag(ItemTags.FOOT_ARMOR_ENCHANTABLE).add(CCItems.tanooki_shoes.get()).add(CCItems.thunder_boots.get());
        this.tag(ItemTags.HEAD_ARMOR).add(CCItems.tanooki_hood.get()).add(CCItems.thunder_helmet.get());
        this.tag(ItemTags.CHEST_ARMOR).add(CCItems.tanooki_chest.get()).add(CCItems.thunder_chestplate.get());
        this.tag(ItemTags.LEG_ARMOR).add(CCItems.tanooki_legs.get()).add(CCItems.thunder_leggings.get());
        this.tag(ItemTags.FOOT_ARMOR).add(CCItems.tanooki_shoes.get()).add(CCItems.thunder_boots.get());
        this.tag(Tags.Items.TOOLS_SHIELD).add(CCItems.GIANTSHELL.get(), CCItems.giantTurtleShell.get());
        this.tag(ItemTags.DURABILITY_ENCHANTABLE).add(CCItems.tanooki_hood.get(), CCItems.tanooki_chest.get(), CCItems.tanooki_legs.get(), CCItems.tanooki_shoes.get(), CCItems.thunder_helmet.get(), CCItems.thunder_chestplate.get(), CCItems.thunder_leggings.get(), CCItems.thunder_boots.get());
        this.tag(ItemTags.VANISHING_ENCHANTABLE).add(CCItems.tanooki_hood.get(), CCItems.tanooki_chest.get(), CCItems.tanooki_legs.get(), CCItems.tanooki_shoes.get(), CCItems.thunder_helmet.get(), CCItems.thunder_chestplate.get(), CCItems.thunder_leggings.get(), CCItems.thunder_boots.get());
    }
}
