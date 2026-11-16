package com.keletu.calamitycurios.init;

import com.keletu.calamitycurios.CalamityCurios;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class CCDataComponents {

    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, CalamityCurios.MODID);
    public static final Supplier<DataComponentType<Long>> MECHANICAL_HEART_ENERGY = COMPONENTS.register("mechanical_heart_energy",
            () -> DataComponentType.<Long>builder().persistent(Codec.LONG).networkSynchronized(ByteBufCodecs.VAR_LONG).build());

    private CCDataComponents() {
    }
}
