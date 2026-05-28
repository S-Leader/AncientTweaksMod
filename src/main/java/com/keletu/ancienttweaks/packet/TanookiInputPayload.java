package com.keletu.ancienttweaks.packet;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.event.TanookiEvents;
import com.keletu.ancienttweaks.init.ATAttachments;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record TanookiInputPayload(boolean keyDown, boolean clicked) implements CustomPacketPayload {

    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "tanooki_input");

    public static final Type<TanookiInputPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<ByteBuf, TanookiInputPayload> CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.BOOL, TanookiInputPayload::keyDown,
                    ByteBufCodecs.BOOL, TanookiInputPayload::clicked,
                    TanookiInputPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final TanookiInputPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player == null) return;
            if (!TanookiEvents.hasArmorEquipped(player)) return;

            var data = player.getData(ATAttachments.DATA_TYPE);
            data.spinKeyDown = payload.keyDown();

            if (payload.clicked()) {
                data.spinKeyClicked = true;
            }
        });
    }
}