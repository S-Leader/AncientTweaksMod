package com.keletu.ancienttweaks.event;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.AncientTweaksConfig;
import com.keletu.ancienttweaks.init.ATAttachments;
import com.keletu.ancienttweaks.init.ATItems;
import com.keletu.ancienttweaks.packet.PacketBubbleShield;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(modid = AncientTweaks.MODID)
public final class RoverDriveEvents {

    private RoverDriveEvents() {
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            var data = serverPlayer.getData(ATAttachments.DATA_SHIELD);

            PacketDistributor.sendToPlayer(serverPlayer, new PacketBubbleShield(data.heartCount));
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide) {
            return;
        }

        var data = player.getData(ATAttachments.DATA_SHIELD);
        int maxc = circulateMaxCooldown(player) * 20;
        int maxs = circulateMaxAbsorb(player);

        if (data.heartCount > maxs) {
            data.heartCount = maxs;
        }
        if (maxc == 0) {
            removeBubbleArmor(player);
            removeBubbleArmorSponge(player);
            return;
        } else {
            if (data.cooldown < maxc && data.heartCount != maxs) {
                data.cooldown++;
            } else {
                if (data.heartCount < maxs) {
                    data.heartCount++;
                } else {
                    data.cooldown = 0;
                }
            }
        }

        if (data.heartCount > 0) {
            if (hasRoverDrive(player)) {
                applyBubbleArmor(player);
            }

            if (hasSponge(player)) {
                applyBubbleArmorSponge(player);
            }

        } else {
            removeBubbleArmor(player);
            removeBubbleArmorSponge(player);
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (player.level().isClientSide) {
            return;
        }


        var data = player.getData(ATAttachments.DATA_SHIELD);
        int maxc = circulateMaxCooldown(player) * 20;

        if (maxc != 0) {
            data.cooldown = 0;
        }
    }

    /**
     * Soul hearts absorb final damage.
     * <p>
     * LivingDamageEvent runs after armor/enchant reductions,
     * which is similar to your old "Fix shields ignore armor" behavior.
     */
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (player.level().isClientSide) {
            return;
        }

        if (event.getNewDamage() <= 0) {
            return;
        }
        var data = player.getData(ATAttachments.DATA_SHIELD);

        if (data.heartCount <= 0) {
            return;
        }

        float damage = event.getNewDamage();

        if (damage <= 0) {
            return;
        }

        if (hasSponge(player))
            damage *= 1 - AncientTweaksConfig.CONFIG_CALAMITY.theSpongeDamageDiscount.get().floatValue();

        if (data.heartCount >= damage) {
            data.heartCount -= damage;
            damage = 0;
        } else {
            damage -= data.heartCount;
            data.heartCount = 0;
        }


        event.setNewDamage(damage);
    }

    public static void applyBubbleArmor(Player player) {
        AttributeInstance armor = player.getAttribute(Attributes.ARMOR);

        if (armor == null) {
            return;
        }

        if (armor.getModifier(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "bubble_armor")) != null) {
            return;
        }

        AttributeModifier modifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "bubble_armor"), AncientTweaksConfig.CONFIG_CALAMITY.roverDriveShieldArmor.get(), AttributeModifier.Operation.ADD_VALUE);

        armor.addTransientModifier(modifier);
    }

    public static void removeBubbleArmor(Player player) {
        AttributeInstance armor = player.getAttribute(Attributes.ARMOR);

        if (armor == null) {
            return;
        }

        if (armor.getModifier(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "bubble_armor")) != null) {
            armor.removeModifier(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "bubble_armor"));
        }
    }


    private static boolean hasRoverDrive(Player player) {
        return CuriosApi.getCuriosInventory(player).map(handler -> handler.findFirstCurio(ATItems.roverDrive.get()).isPresent()).orElse(false);
    }

    private static boolean hasSponge(Player player) {
        return CuriosApi.getCuriosInventory(player).map(handler -> handler.findFirstCurio(ATItems.theSponge.get()).isPresent()).orElse(false);
    }

    private static void setCooldown(Player player, int cooldown) {
        var data = player.getData(ATAttachments.DATA_SHIELD);
        data.cooldown = cooldown;
    }

    public static void applyBubbleArmorSponge(Player player) {
        AttributeInstance armor = player.getAttribute(Attributes.ARMOR);

        if (armor == null) {
            return;
        }

        if (armor.getModifier(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "sponge_bubble_armor")) != null) {
            return;
        }

        AttributeModifier modifier = new AttributeModifier(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "sponge_bubble_armor"), AncientTweaksConfig.CONFIG_CALAMITY.theSpongeShieldArmor.get(), AttributeModifier.Operation.ADD_VALUE);

        armor.addTransientModifier(modifier);
    }

    public static void removeBubbleArmorSponge(Player player) {
        AttributeInstance armor = player.getAttribute(Attributes.ARMOR);

        if (armor == null) {
            return;
        }

        if (armor.getModifier(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "sponge_bubble_armor")) != null) {
            armor.removeModifier(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "sponge_bubble_armor"));
        }
    }

    private static int circulateMaxCooldown(Player player) {
        var data = player.getData(ATAttachments.DATA_SHIELD);
        int max = 0;
        if (hasRoverDrive(player)) {
            max += AncientTweaksConfig.CONFIG_CALAMITY.roverDriveShieldCooldown.get();
        }

        if (ThunderEvents.hasArmorEquipped(player)) {
            max += 7;
        }

        if (hasSponge(player)) {
            max += AncientTweaksConfig.CONFIG_CALAMITY.theSpongeShieldCooldown.get();
        }

        return max;
    }

    private static int circulateMaxAbsorb(Player player) {
        int max = 0;
        if (hasRoverDrive(player)) {
            max += AncientTweaksConfig.CONFIG_CALAMITY.roverDriveShieldCount.get();
        }

        if (ThunderEvents.hasArmorEquipped(player)) {
            max += 8;
        }

        if (hasSponge(player)) {
            max += AncientTweaksConfig.CONFIG_CALAMITY.theSpongeShieldCount.get();
        }

        return max;
    }
}
