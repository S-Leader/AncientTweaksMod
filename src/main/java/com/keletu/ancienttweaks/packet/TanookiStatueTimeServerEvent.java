package com.keletu.ancienttweaks.packet;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.init.ATAttachments;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record TanookiStatueTimeServerEvent(int isStatue) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "stone_statue_time");
    public static final StreamCodec<ByteBuf, TanookiStatueTimeServerEvent> CODEC = StreamCodec.composite(ByteBufCodecs.INT, TanookiStatueTimeServerEvent::isStatue, TanookiStatueTimeServerEvent::new);

    public static final Type<TanookiStatueTimeServerEvent> TYPE = new Type<>(ID);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final TanookiStatueTimeServerEvent payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player instanceof ServerPlayer) {

                player.getData(ATAttachments.DATA_TYPE).statueTime = payload.isStatue;
            }
        });
    }
}