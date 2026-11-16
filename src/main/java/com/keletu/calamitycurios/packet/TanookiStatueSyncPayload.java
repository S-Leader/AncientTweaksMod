package com.keletu.calamitycurios.packet;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.init.CCAttachments;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record TanookiStatueSyncPayload(int statueTime, boolean isStatue, int entityId) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(CalamityCurios.MODID, "tanooki_statue_sync");

    public static final Type<TanookiStatueSyncPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<ByteBuf, TanookiStatueSyncPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, TanookiStatueSyncPayload::statueTime,
            ByteBufCodecs.BOOL, TanookiStatueSyncPayload::isStatue,
            ByteBufCodecs.INT, TanookiStatueSyncPayload::entityId,
            TanookiStatueSyncPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final TanookiStatueSyncPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Player localPlayer = context.player();
            if (localPlayer == null) return;
            if (localPlayer.level() == null) return;

            var entity = localPlayer.level().getEntity(payload.entityId());
            if (!(entity instanceof Player targetPlayer)) return;

            var data = targetPlayer.getData(CCAttachments.DATA_TYPE);
            data.statueTime = payload.statueTime();
            data.isStatue = payload.isStatue();
        });
    }
}