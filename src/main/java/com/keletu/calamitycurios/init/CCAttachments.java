package com.keletu.calamitycurios.init;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.cap.*;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class CCAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, CalamityCurios.MODID);

    public static final Supplier<AttachmentType<HurtCounter>> HURT_COUNTER = ATTACHMENT_TYPES.register("hurt_counter", () -> AttachmentType.serializable(HurtCounter::new).copyOnDeath().build());

    public static final Supplier<AttachmentType<CommunityProgress>> COMMUNITY_PROGRESS = ATTACHMENT_TYPES.register("community_progress", () -> AttachmentType.serializable(CommunityProgress::new).copyOnDeath().build());

    public static final Supplier<AttachmentType<MechanicalHeartData>> MECHANICAL_HEART = ATTACHMENT_TYPES.register("mechanical_heart", () -> AttachmentType.builder(MechanicalHeartData::new).build());

    public static final Supplier<AttachmentType<TanookiData>> DATA_TYPE = ATTACHMENT_TYPES.register("tanooki_data", () -> AttachmentType.builder(TanookiData::new).build());

    public static final Supplier<AttachmentType<ShieldData>> DATA_SHIELD = ATTACHMENT_TYPES.register("shield_counter", () -> AttachmentType.builder(ShieldData::new).build());
}
