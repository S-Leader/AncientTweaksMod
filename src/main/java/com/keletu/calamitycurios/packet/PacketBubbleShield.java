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

public record PacketBubbleShield(int hearts) implements CustomPacketPayload {

    public static final Type<PacketBubbleShield> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(CalamityCurios.MODID, "bubble_shield"));

    public static final StreamCodec<ByteBuf, PacketBubbleShield> CODEC = StreamCodec.composite(ByteBufCodecs.VAR_INT, PacketBubbleShield::hearts, PacketBubbleShield::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final PacketBubbleShield packet, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            player.getData(CCAttachments.DATA_SHIELD).heartCount = packet.hearts();
        });
    }
}