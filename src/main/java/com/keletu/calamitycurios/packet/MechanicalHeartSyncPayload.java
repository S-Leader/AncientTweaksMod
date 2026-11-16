package com.keletu.calamitycurios.packet;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.init.CCAttachments;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MechanicalHeartSyncPayload(boolean equipped, long energy, int experiencePerLevel,
                                         int maximumLevel) implements CustomPacketPayload {

    public static final Type<MechanicalHeartSyncPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(CalamityCurios.MODID, "mechanical_heart_sync"));
    public static final StreamCodec<ByteBuf, MechanicalHeartSyncPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, MechanicalHeartSyncPayload::equipped,
            ByteBufCodecs.VAR_LONG, MechanicalHeartSyncPayload::energy,
            ByteBufCodecs.VAR_INT, MechanicalHeartSyncPayload::experiencePerLevel,
            ByteBufCodecs.VAR_INT, MechanicalHeartSyncPayload::maximumLevel,
            MechanicalHeartSyncPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(MechanicalHeartSyncPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> context.player().getData(CCAttachments.MECHANICAL_HEART)
                .update(payload.equipped(), payload.energy(), payload.experiencePerLevel(), payload.maximumLevel()));
    }
}
