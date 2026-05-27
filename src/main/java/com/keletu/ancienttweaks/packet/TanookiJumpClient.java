package com.keletu.ancienttweaks.packet;

import com.keletu.ancienttweaks.AncientTweaks;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record TanookiJumpClient() implements CustomPacketPayload {
    public static final Type<TanookiJumpClient> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "tanooki_jump_client"));
    public static final StreamCodec<ByteBuf, TanookiJumpClient> CODEC = StreamCodec.unit(new TanookiJumpClient());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(TanookiJumpClient payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = Minecraft.getInstance().player;
            if (player == null) return;

            player.jumpFromGround();
        });
    }
}