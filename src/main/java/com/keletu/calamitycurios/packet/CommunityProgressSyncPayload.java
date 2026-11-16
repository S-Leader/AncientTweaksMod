package com.keletu.calamitycurios.packet;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.init.CCAttachments;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record CommunityProgressSyncPayload(int bossCount, int maximum) implements CustomPacketPayload {

    public static final Type<CommunityProgressSyncPayload> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(CalamityCurios.MODID, "community_progress"));
    public static final StreamCodec<ByteBuf, CommunityProgressSyncPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, CommunityProgressSyncPayload::bossCount,
            ByteBufCodecs.VAR_INT, CommunityProgressSyncPayload::maximum,
            CommunityProgressSyncPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(CommunityProgressSyncPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> context.player().getData(CCAttachments.COMMUNITY_PROGRESS)
                .receiveSync(payload.bossCount(), payload.maximum()));
    }
}
