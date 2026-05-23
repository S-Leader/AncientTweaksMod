package com.keletu.ancienttweaks.packet;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.baubles.soulheart.SoulHeartClientHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PacketBubbleShield(int hearts) implements CustomPacketPayload {

    public static final Type<PacketBubbleShield> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "bubble_shield")
    );

    public static final StreamCodec<ByteBuf, PacketBubbleShield> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            PacketBubbleShield::hearts,
            PacketBubbleShield::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final PacketBubbleShield packet, final IPayloadContext context) {
        context.enqueueWork(() -> {
            SoulHeartClientHandler.clientPlayerHP = packet.hearts();
        });
    }
}