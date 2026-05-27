package com.keletu.ancienttweaks.packet;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.event.TanookiEvents;
import com.keletu.ancienttweaks.init.ATSounds;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public record TanookiRolling() implements CustomPacketPayload {
    public static final Type<TanookiRolling> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "tanooki_roll"));
    public static final StreamCodec<ByteBuf, TanookiRolling> CODEC = StreamCodec.unit(new TanookiRolling());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    // 服务端处理逻辑
    public static void handle(TanookiRolling payload, IPayloadContext context) {
// 在 context.enqueueWork() 内部执行:
        Player player = context.player();
        if (player != null && player.level() instanceof ServerLevel serverLevel) {
            if (player.getDeltaMovement().horizontalDistance() > 0.01F || !TanookiEvents.hasArmorEquipped(player) || player.getVehicle() != null) {
                return;
            }
            // 1. 定义攻击范围：以玩家为中心，X和Z方向向外扩展 2.5 格，Y方向扩展 1 格
            AABB attackBox = player.getBoundingBox().inflate(2.5D, 1.0D, 2.5D);

            // 2. 获取范围内的所有存活生物（排除玩家自己）
            List<LivingEntity> targets = serverLevel.getEntitiesOfClass(LivingEntity.class, attackBox, entity -> entity != player && entity.isAlive());

            boolean hitAnything = false;

            // 3. 遍历并造成伤害
            for (LivingEntity target : targets) {
                // 造成 6 点（3颗心）的玩家攻击伤害
                target.hurt(player.damageSources().playerAttack(player), 6.0F);

                // 可选：附加一点击退效果，模拟尾巴扫开的物理反馈
                target.knockback(0.5D, player.getX() - target.getX(), player.getZ() - target.getZ());
                hitAnything = true;
            }

            // 4. 播放特效和音效（无论是否打到怪都可以播放，增加手感）
            serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(), ATSounds.SWEEP.value(), SoundSource.PLAYERS, 1.0F, 1.0F);

            // 生成横扫的剑气白光粒子
            serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, player.getX(), player.getY() + 0.5, player.getZ(), 1, 0, 0, 0, 0);
        }

    }
}