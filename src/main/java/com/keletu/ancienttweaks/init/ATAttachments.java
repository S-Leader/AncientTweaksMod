package com.keletu.ancienttweaks.init;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.cap.HurtCounter;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ATAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, AncientTweaks.MODID);

    public static final Supplier<AttachmentType<HurtCounter>> HURT_COUNTER =
            ATTACHMENT_TYPES.register("hurt_counter", () ->
                    AttachmentType.serializable(HurtCounter::new)
                            .copyOnDeath()
                            .build()
            );
}