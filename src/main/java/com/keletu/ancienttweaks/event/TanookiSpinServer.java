package com.keletu.ancienttweaks.event;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.init.ATAttachments;
import com.keletu.ancienttweaks.init.ATSounds;
import com.keletu.ancienttweaks.packet.TanookiJumpClient;
import com.keletu.ancienttweaks.packet.TanookiStatueSyncPayload;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

@EventBusSubscriber(modid = AncientTweaks.MODID)
public class TanookiSpinServer {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) return;
        if (player.getVehicle() != null) return;
        if (!TanookiEvents.hasArmorEquipped(player)) return;

        var data = player.getData(ATAttachments.DATA_TYPE);
        boolean inAir = !player.onGround();
        int wasStatueThisTick = data.statueTime;

        if (data.spinKeyDown && inAir && data.statueTime == 0) {
            data.statueTime = 100;
            data.isStatue = true;
        } else if (data.statueTime == 0 && !inAir) {
            data.isStatue = false;
        }

        if (data.statueTime > 0) {
            data.statueTime--;
            data.isStatue = true;
        } else {
            data.isStatue = false;
        }

        if (data.isStatue) {
            if (!data.statueSetInvulnerable && !player.getAbilities().invulnerable) {
                player.getAbilities().invulnerable = true;
                data.statueSetInvulnerable = true;
                player.onUpdateAbilities();
            }
        } else {
            if (data.statueSetInvulnerable) {
                player.getAbilities().invulnerable = false;
                data.statueSetInvulnerable = false;
                player.onUpdateAbilities();
            }
        }

        if (data.spinKeyClicked) {
            data.spinKeyClicked = false;

            if (wasStatueThisTick <= 0 && !inAir && data.spinTicks == 0) {
                boolean isStationary = player.getDeltaMovement().horizontalDistanceSqr() < 0.001;

                if (isStationary) {
                    data.spinTicks = 8;

                    AABB attackBox = player.getBoundingBox().inflate(2.5D, 1.0D, 2.5D);
                    List<LivingEntity> targets = player.level().getEntitiesOfClass(LivingEntity.class, attackBox, entity -> entity != player && entity.isAlive());

                    for (LivingEntity target : targets) {
                        target.hurt(player.damageSources().playerAttack(player), 10.0F);
                        target.knockback(0.5D, player.getX() - target.getX(), player.getZ() - target.getZ());
                    }

                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), ATSounds.SWEEP.value(), SoundSource.PLAYERS, 1.0F, 1.0F);

                    ((ServerLevel) player.level()).sendParticles(ParticleTypes.SWEEP_ATTACK, player.getX(), player.getY() + 0.5, player.getZ(), 1, 0, 0, 0, 0);
                }
            }
        }

        if (player instanceof ServerPlayer serverPlayer) {
            TanookiStatueSyncPayload payload = new TanookiStatueSyncPayload(data.statueTime, data.isStatue, player.getId());

            PacketDistributor.sendToPlayer(serverPlayer, payload);
            PacketDistributor.sendToPlayersTrackingEntity(serverPlayer, payload);
        }

        if (data.spinTicks > 0) {
            PacketDistributor.sendToPlayer((ServerPlayer) player, new TanookiJumpClient(3));
            data.spinTicks--;
        }
    }
}
