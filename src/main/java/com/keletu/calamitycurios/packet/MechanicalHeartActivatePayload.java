package com.keletu.calamitycurios.packet;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.event.MechanicalHeartEvents;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MechanicalHeartActivatePayload() implements CustomPacketPayload {

    public static final Type<MechanicalHeartActivatePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(CalamityCurios.MODID, "mechanical_heart_activate"));
    public static final StreamCodec<ByteBuf, MechanicalHeartActivatePayload> CODEC = StreamCodec.unit(new MechanicalHeartActivatePayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(MechanicalHeartActivatePayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                MechanicalHeartEvents.activate(player);
            }
        });
    }
}
