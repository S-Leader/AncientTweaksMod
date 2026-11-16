package com.keletu.calamitycurios.event;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.CalamityCuriosConfig;
import com.keletu.calamitycurios.baubles.community.CommunityBonuses;
import com.keletu.calamitycurios.init.CCAttachments;
import com.keletu.calamitycurios.init.CCItems;
import com.keletu.calamitycurios.packet.CommunityProgressSyncPayload;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(modid = CalamityCurios.MODID)
public final class TheCommunityEvents {

    public static final ResourceLocation MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(CalamityCurios.MODID, "the_community");

    private TheCommunityEvents() {
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBossDeath(LivingDeathEvent event) {
        LivingEntity victim = event.getEntity();
        if (victim.level().isClientSide || victim instanceof Player) {
            return;
        }
        ServerPlayer killer = event.getSource().getEntity() instanceof ServerPlayer direct ? direct : victim.getKillCredit() instanceof ServerPlayer credited ? credited : null;
        ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(victim.getType());
        if (killer == null || !isAllowedBoss(id.toString())) {
            return;
        }
        if (killer.getData(CCAttachments.COMMUNITY_PROGRESS).recordKill(id)) {
            updateBonuses(killer);
            syncProgress(killer);
            if (isEquipped(killer)) {
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            updateBonuses(player);
            int count = eligibleBossCount(player);
            if (player.getData(CCAttachments.COMMUNITY_PROGRESS).needsSync(count, maximum())) {
                syncProgress(player, count);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onDamage(LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof ServerPlayer player && event.getNewDamage() > 0 && isEquipped(player)) {
            event.setNewDamage((float) (event.getNewDamage() * bonuses(player).damageMultiplier()));
        }
    }

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            syncProgress(player);
        }
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            syncProgress(player);
        }
    }

    @SubscribeEvent
    public static void onDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            syncProgress(player);
        }
    }

    public static boolean isEquipped(Player player) {
        return CuriosApi.getCuriosInventory(player).map(handler -> handler.findFirstCurio(stack -> stack.is(CCItems.theCommunity.get())).isPresent()).orElse(false);
    }

    public static boolean isAllowedBoss(String id) {
        boolean tagged = BuiltInRegistries.ENTITY_TYPE.getOptional(ResourceLocation.parse(id)).map(type -> type == EntityType.ENDER_DRAGON || type == EntityType.WITHER || type.is(Tags.EntityTypes.BOSSES)).orElse(false);
        return CommunityBonuses.allowsBoss(id, tagged, CalamityCuriosConfig.CONFIG_COMMUNITY.bossWhitelist.get(), CalamityCuriosConfig.CONFIG_COMMUNITY.bossBlacklist.get());
    }

    public static int eligibleBossCount(Player player) {
        return (int) player.getData(CCAttachments.COMMUNITY_PROGRESS).defeatedBosses().stream().filter(TheCommunityEvents::isAllowedBoss).count();
    }

    public static CommunityBonuses bonuses(Player player) {
        return CommunityBonuses.forProgress(eligibleBossCount(player), maximum());
    }

    private static int maximum() {
        return CalamityCuriosConfig.CONFIG_COMMUNITY.maxBossKills.get();
    }

    public static void updateBonuses(ServerPlayer player) {
        applyBonuses(player, isEquipped(player) ? bonuses(player) : new CommunityBonuses(0));
    }

    public static void removeBonuses(ServerPlayer player) {
        applyBonuses(player, new CommunityBonuses(0));
    }

    private static void applyBonuses(Player player, CommunityBonuses bonuses) {
        updateModifier(player, Attributes.MAX_HEALTH, bonuses.maxHealth());
        updateModifier(player, Attributes.ATTACK_DAMAGE, bonuses.attackDamage());
        updateModifier(player, Attributes.MOVEMENT_SPEED, bonuses.movementSpeed());
        updateModifier(player, Attributes.JUMP_STRENGTH, bonuses.jumpStrength());
        updateModifier(player, Attributes.SAFE_FALL_DISTANCE, bonuses.safeFallDistance());
        if (player.getHealth() > player.getMaxHealth()) {
            player.setHealth(player.getMaxHealth());
        }
    }

    private static void updateModifier(Player player, Holder<Attribute> attribute, double amount) {
        AttributeInstance instance = player.getAttribute(attribute);
        if (instance == null) {
            return;
        }
        AttributeModifier existing = instance.getModifier(MODIFIER_ID);
        if (existing != null && existing.amount() == amount && existing.operation() == AttributeModifier.Operation.ADD_VALUE) {
            return;
        }
        instance.removeModifier(MODIFIER_ID);
        if (amount > 0) {
            instance.addTransientModifier(new AttributeModifier(MODIFIER_ID, amount, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    private static void syncProgress(ServerPlayer player) {
        syncProgress(player, eligibleBossCount(player));
    }

    private static void syncProgress(ServerPlayer player, int count) {
        PacketDistributor.sendToPlayer(player, new CommunityProgressSyncPayload(count, maximum()));
        player.getData(CCAttachments.COMMUNITY_PROGRESS).markSent(count, maximum());
    }
}
