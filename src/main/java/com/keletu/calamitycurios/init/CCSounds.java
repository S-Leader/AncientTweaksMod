package com.keletu.calamitycurios.init;

import com.keletu.calamitycurios.CalamityCurios;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CCSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, CalamityCurios.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> SWEEP = SOUNDS.register("sweep", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(CalamityCurios.MODID, "sweep")));
}
