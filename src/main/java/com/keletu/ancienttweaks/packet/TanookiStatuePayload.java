package com.keletu.ancienttweaks.packet;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.init.ATAttachments;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record TanookiStatuePayload(boolean isActivating) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "tanooki_statue");
    public static final StreamCodec<ByteBuf, TanookiStatuePayload> CODEC = StreamCodec.composite(ByteBufCodecs.BOOL, TanookiStatuePayload::isActivating, TanookiStatuePayload::new);

    public static final Type<TanookiStatuePayload> TYPE = new Type<>(ID);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final TanookiStatuePayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null) {
                boolean isStatue = payload.isActivating();
                var data = player.getData(ATAttachments.DATA_TYPE);

                data.isStatue = isStatue;
            }
        });
    }
}