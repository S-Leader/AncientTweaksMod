package com.keletu.ancienttweaks.init;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.cap.HurtCounter;
import com.keletu.ancienttweaks.cap.TanookiData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ATAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, AncientTweaks.MODID);

    public static final Supplier<AttachmentType<HurtCounter>> HURT_COUNTER = ATTACHMENT_TYPES.register("hurt_counter", () -> AttachmentType.serializable(HurtCounter::new).copyOnDeath().build());

    public static final Supplier<AttachmentType<TanookiData>> DATA_TYPE = ATTACHMENT_TYPES.register("tanooki_data", () -> AttachmentType.builder(TanookiData::new).build());
}