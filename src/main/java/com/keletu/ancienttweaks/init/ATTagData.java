package com.keletu.ancienttweaks.init;

import com.keletu.ancienttweaks.AncientTweaks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ATTagData extends ItemTagsProvider {
    public ATTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper helper) {
        super(output, registries, blockTags, AncientTweaks.MODID, helper);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(ItemTags.HEAD_ARMOR_ENCHANTABLE).add(ATItems.tanooki_hood.get());
        this.tag(ItemTags.CHEST_ARMOR_ENCHANTABLE).add(ATItems.tanooki_chest.get());
        this.tag(ItemTags.LEG_ARMOR_ENCHANTABLE).add(ATItems.tanooki_legs.get());
        this.tag(ItemTags.FOOT_ARMOR_ENCHANTABLE).add(ATItems.tanooki_shoes.get());
        this.tag(ItemTags.HEAD_ARMOR).add(ATItems.tanooki_hood.get());
        this.tag(ItemTags.CHEST_ARMOR).add(ATItems.tanooki_chest.get());
        this.tag(ItemTags.LEG_ARMOR).add(ATItems.tanooki_legs.get());
        this.tag(ItemTags.FOOT_ARMOR).add(ATItems.tanooki_shoes.get());
        this.tag(Tags.Items.TOOLS_SHIELD).add(ATItems.GIANTSHELL.get(), ATItems.giantTurtleShell.get());
        this.tag(ItemTags.DURABILITY_ENCHANTABLE).add(ATItems.tanooki_hood.get(), ATItems.tanooki_chest.get(), ATItems.tanooki_legs.get(), ATItems.tanooki_shoes.get());
        this.tag(ItemTags.VANISHING_ENCHANTABLE).add(ATItems.tanooki_hood.get(), ATItems.tanooki_chest.get(), ATItems.tanooki_legs.get(), ATItems.tanooki_shoes.get());
    }
}