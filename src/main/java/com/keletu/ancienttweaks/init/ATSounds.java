package com.keletu.ancienttweaks.init;

import com.keletu.ancienttweaks.AncientTweaks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ATSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, AncientTweaks.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> SWEEP = SOUNDS.register("sweep", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "sweep")));
}
