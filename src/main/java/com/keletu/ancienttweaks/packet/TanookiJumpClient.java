package com.keletu.ancienttweaks.packet;

import com.keletu.ancienttweaks.AncientTweaks;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record TanookiJumpClient(int i) implements CustomPacketPayload {
    public static final Type<TanookiJumpClient> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "tanooki_jump_client"));
    public static final StreamCodec<ByteBuf, TanookiJumpClient> CODEC = StreamCodec.composite(ByteBufCodecs.VAR_INT, TanookiJumpClient::i, TanookiJumpClient::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(TanookiJumpClient payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player == null) return;

            if (payload.i() == 1) player.jumpFromGround();


            if (payload.i() == 2) player.setDeltaMovement(0, -1.5, 0);

            if (payload.i() == 3) player.turn(300, 0);
        });
    }
}