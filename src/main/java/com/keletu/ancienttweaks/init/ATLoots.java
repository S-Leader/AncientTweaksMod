package com.keletu.ancienttweaks.init;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.event.DrownedJellyLootModifier;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ATLoots {

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOTS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, AncientTweaks.MODID);

    public static final Supplier<MapCodec<? extends IGlobalLootModifier>> DROWNED_JELLY =
            LOOTS.register(
                    "drowned_jelly",
                    () -> DrownedJellyLootModifier.CODEC
            );
}