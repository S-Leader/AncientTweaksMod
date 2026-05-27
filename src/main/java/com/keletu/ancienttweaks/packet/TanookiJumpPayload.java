package com.keletu.ancienttweaks.packet;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.cap.TanookiData;
import com.keletu.ancienttweaks.init.ATAttachments;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record TanookiJumpPayload() implements CustomPacketPayload {
    public static final Type<TanookiJumpPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "tanooki_jump"));
    public static final StreamCodec<ByteBuf, TanookiJumpPayload> CODEC = StreamCodec.unit(new TanookiJumpPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    // 服务端处理逻辑
    public static void handle(TanookiJumpPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player == null) return;

            TanookiData data = player.getData(ATAttachments.DATA_TYPE);
            // 验证服务端：玩家在空中，并且技能已激活
            if (data.isPoweredUp && !player.onGround()) {
                player.jumpFromGround(); // 服务端起跳：增加跳跃统计、消耗饥饿度、触发声音
                player.fallDistance = 0.0F; // 重置跌落伤害

                ((ServerLevel) player.level()).sendParticles(ParticleTypes.CLOUD, player.getX(), player.getY() + 0.2, player.getZ(), 8, 0.25, 0.1, 0.25, 0.02);
            }
        });
    }
}