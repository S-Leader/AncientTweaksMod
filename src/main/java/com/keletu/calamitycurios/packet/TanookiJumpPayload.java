package com.keletu.calamitycurios.packet;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.cap.TanookiData;
import com.keletu.calamitycurios.event.TanookiEvents;
import com.keletu.calamitycurios.init.CCAttachments;
import com.keletu.calamitycurios.init.CCSounds;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record TanookiJumpPayload() implements CustomPacketPayload {
    public static final Type<TanookiJumpPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(CalamityCurios.MODID, "tanooki_jump"));
    public static final StreamCodec<ByteBuf, TanookiJumpPayload> CODEC = StreamCodec.unit(new TanookiJumpPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(TanookiJumpPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player == null) return;

            TanookiData data = player.getData(CCAttachments.DATA_TYPE);
            if (data.isPoweredUp && !player.onGround() && player.getFoodData().getFoodLevel() > 6 && TanookiEvents.hasArmorEquipped(player)) {
                PacketDistributor.sendToPlayer((ServerPlayer) player, new TanookiJumpClient(1));
                player.causeFoodExhaustion(2.0F);
                player.fallDistance = 0.0F;
                player.level().playSound(null, player.blockPosition(), CCSounds.SWEEP.value(), SoundSource.PLAYERS, 1.0F, 1.0F);

                ((ServerLevel) player.level()).sendParticles(ParticleTypes.CLOUD, player.getX(), player.getY() + 0.2, player.getZ(), 8, 0.25, 0.1, 0.25, 0.02);
            }
        });
    }
}