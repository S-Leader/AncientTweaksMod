package com.keletu.ancienttweaks.event;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.init.ATAttachments;
import com.keletu.ancienttweaks.packet.TanookiJumpClient;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = AncientTweaks.MODID)
public class TanookiSmashHandler {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide && player.getData(ATAttachments.DATA_TYPE).isStatue) {
            player.hurtMarked = true;
        }
    }

    @SubscribeEvent
    public static void onPlayerFall(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide && TanookiEvents.hasArmorEquipped(player)) {
            if (player.getData(ATAttachments.DATA_TYPE).isStatue) {

                event.setDamageMultiplier(0.0F);
                float fallDistance = event.getDistance();

                if (fallDistance > 2.0F && player.level() instanceof ServerLevel serverLevel) {
                    float smashDamage = 5.0F + (fallDistance * 1.5F);

                    AABB smashBox = player.getBoundingBox().inflate(3.5D, 1.5D, 3.5D);
                    for (LivingEntity target : serverLevel.getEntitiesOfClass(LivingEntity.class, smashBox, e -> e != player && e.isAlive())) {

                        target.hurt(player.damageSources().playerAttack(player), smashDamage);

                        double pushX = target.getX() - player.getX();
                        double pushZ = target.getZ() - player.getZ();
                        target.knockback(1.0D, -pushX, -pushZ);
                    }

                    PacketDistributor.sendToPlayer((ServerPlayer) player, new TanookiJumpClient(2));
                    serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.MACE_SMASH_GROUND_HEAVY, SoundSource.PLAYERS, 1.0F, 0.7F);
                    serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0F, 0.7F);

                    serverLevel.sendParticles(ParticleTypes.EXPLOSION, player.getX(), player.getY(), player.getZ(), 1, 0, 0, 0, 0);
                    serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, player.getX(), player.getY(), player.getZ(), 30, 1.5, 0.2, 1.5, 0.1);
                }

                player.getData(ATAttachments.DATA_TYPE).isStatue = false;
            }
        }
    }
}