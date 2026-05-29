package com.keletu.ancienttweaks.init;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import static org.openjdk.nashorn.internal.runtime.Debug.id;

import java.util.concurrent.CompletableFuture;

public class ATRecipes extends RecipeProvider {
    public ATRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        thunderArmorRecipes(output, ATItems.tanooki_hood.get(), "tanooki_hood", ATTags.TANOOKI_CORE, ATTags.TANOOKI_MAT, Blocks.DIAMOND_BLOCK.asItem(), Items.LEATHER, "MMM", "MCM");
        thunderArmorRecipes(output, ATItems.tanooki_chest.get(), "tanooki_chest", ATTags.TANOOKI_CORE, ATTags.TANOOKI_MAT, Blocks.DIAMOND_BLOCK.asItem(), Items.LEATHER, "MCM", "MMM", "MMM");
        thunderArmorRecipes(output, ATItems.tanooki_legs.get(), "tanooki_legs", ATTags.TANOOKI_CORE, ATTags.TANOOKI_MAT, Blocks.DIAMOND_BLOCK.asItem(), Items.LEATHER, "MMM", "MCM", "M M");
        thunderArmorRecipes(output, ATItems.tanooki_shoes.get(), "tanooki_shoes", ATTags.TANOOKI_CORE, ATTags.TANOOKI_MAT, Blocks.DIAMOND_BLOCK.asItem(), Items.LEATHER, "M M", "MCM");

        thunderArmorRecipes(output, ATItems.thunder_helmet.get(), "thunder_helmet", ATTags.THUNDER_CORE, ATTags.THUNDER_MAT, Blocks.REDSTONE_BLOCK.asItem(), Items.IRON_INGOT, "MMM", "MCM");
        thunderArmorRecipes(output, ATItems.thunder_chestplate.get(), "thunder_chestplate", ATTags.THUNDER_CORE, ATTags.THUNDER_MAT, Blocks.REDSTONE_BLOCK.asItem(), Items.IRON_INGOT, "MCM", "MMM", "MMM");
        thunderArmorRecipes(output, ATItems.thunder_leggings.get(), "thunder_leggings", ATTags.THUNDER_CORE, ATTags.THUNDER_MAT, Blocks.REDSTONE_BLOCK.asItem(), Items.IRON_INGOT, "MMM", "MCM", "M M");
        thunderArmorRecipes(output, ATItems.thunder_boots.get(), "thunder_boots", ATTags.THUNDER_CORE, ATTags.THUNDER_MAT, Blocks.REDSTONE_BLOCK.asItem(), Items.IRON_INGOT, "M M", "MCM");
    }

    private void thunderArmorRecipes(RecipeOutput output, ItemLike result, String baseId, TagKey core, TagKey mat, Item coreV, Item matV, String... patterns) {
        for (boolean hasTwilightForest : new boolean[]{false, true}) {
            for (boolean hasGenesis : new boolean[]{false, true}) {
                thunderArmorRecipe(output, result, baseId, patterns, hasTwilightForest, hasGenesis, core, mat, coreV, matV);
            }
        }
    }

    private void thunderArmorRecipe(RecipeOutput output, ItemLike result, String baseId, String[] patterns, boolean hasTwilightForest, boolean hasGenesis, TagKey core, TagKey mat, Item coreV, Item matV) {
        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result);

        for (String pattern : patterns) {
            builder.pattern(pattern);
        }

        if (hasTwilightForest) {
            builder.define('M', mat);
        } else {
            builder.define('M', matV);
        }

        if (hasGenesis) {
            builder.define('C', core);
        } else {
            builder.define('C', coreV);
        }
        builder.save(output.withConditions(modCondition("twilightforest", hasTwilightForest), modCondition("aether_genesis", hasGenesis)), id(thunderRecipeId(baseId, hasTwilightForest, hasGenesis)));
    }

    private static ICondition modCondition(String modid, boolean loaded) {
        ICondition condition = new ModLoadedCondition(modid);
        return loaded ? condition : new NotCondition(condition);
    }

    private static String thunderRecipeId(String baseId, boolean hasTwilightForest, boolean hasGenesis) {
        StringBuilder id = new StringBuilder(baseId);

        if (hasTwilightForest) {
            id.append("_twilightforest");
        }

        if (hasGenesis) {
            id.append("_genesis");
        }

        return id.toString();
    }

}
