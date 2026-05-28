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

    public static void handle(TanookiRolling payload, IPayloadContext context) {
        Player player = context.player();
        if (player != null && player.level() instanceof ServerLevel serverLevel) {
            if (player.getDeltaMovement().horizontalDistance() > 0.01F || !TanookiEvents.hasArmorEquipped(player) || player.getVehicle() != null) {
                return;
            }
            AABB attackBox = player.getBoundingBox().inflate(2.5D, 1.0D, 2.5D);

            List<LivingEntity> targets = serverLevel.getEntitiesOfClass(LivingEntity.class, attackBox, entity -> entity != player && entity.isAlive());

            boolean hitAnything = false;

            for (LivingEntity target : targets) {
                target.hurt(player.damageSources().playerAttack(player), 10.0F);

                target.knockback(0.5D, player.getX() - target.getX(), player.getZ() - target.getZ());
                hitAnything = true;
            }

            serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(), ATSounds.SWEEP.value(), SoundSource.PLAYERS, 1.0F, 1.0F);

            serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, player.getX(), player.getY() + 0.5, player.getZ(), 1, 0, 0, 0, 0);
        }

    }
}