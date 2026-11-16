package com.keletu.calamitycurios.event;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.CalamityCuriosConfig;
import com.keletu.calamitycurios.baubles.ItemMechanicalHeart;
import com.keletu.calamitycurios.init.CCAttachments;
import com.keletu.calamitycurios.init.CCItems;
import com.keletu.calamitycurios.packet.MechanicalHeartSyncPayload;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;

@EventBusSubscriber(modid = CalamityCurios.MODID)
public final class MechanicalHeartEvents {

    private MechanicalHeartEvents() {
    }

    public static ItemStack findEquipped(Player player) {
        return CuriosApi.getCuriosInventory(player)
                .flatMap(handler -> handler.findFirstCurio(CCItems.MECHANICAL_HEART.get()))
                .map(result -> result.stack()).orElse(ItemStack.EMPTY);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onExperience(PlayerXpEvent.XpChange event) {
        if (!(event.getEntity() instanceof ServerPlayer player) || event.getAmount() <= 0) {
            return;
        }
        ItemStack stack = findEquipped(player);
        if (stack.isEmpty()) {
            return;
        }
        var config = CalamityCuriosConfig.CONFIG_MECHANICAL_HEART;
        long capacity = (long) config.experiencePerLevel.get() * config.maximumLevel.get();
        long energy = ItemMechanicalHeart.energy(stack);
        int absorbed = (int) Math.min(event.getAmount(), Math.max(0L, capacity - energy));
        if (absorbed > 0) {
            ItemMechanicalHeart.setEnergy(stack, energy + absorbed);
            event.setAmount(event.getAmount() - absorbed);
            sync(player, stack);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            sync(player, findEquipped(player));
        }
    }

    public static void activate(ServerPlayer player) {
        if (!player.isAlive() || player.isSpectator()) {
            return;
        }
        ItemStack stack = findEquipped(player);
        if (stack.isEmpty()) {
            return;
        }
        var config = CalamityCuriosConfig.CONFIG_MECHANICAL_HEART;
        long cost = (long) config.experiencePerLevel.get() * config.levelsPerUse.get();
        long energy = ItemMechanicalHeart.energy(stack);
        if (energy < cost) {
            player.displayClientMessage(Component.translatable("message.calamitycurios.mechanical_heart.insufficient").withStyle(ChatFormatting.DARK_RED), true);
            return;
        }
        ItemMechanicalHeart.setEnergy(stack, energy - cost);
        player.eat(player.level(), new ItemStack(Items.GOLDEN_APPLE));
        sync(player, stack);
    }

    private static void sync(ServerPlayer player, ItemStack stack) {
        var config = CalamityCuriosConfig.CONFIG_MECHANICAL_HEART;
        var data = player.getData(CCAttachments.MECHANICAL_HEART);
        boolean equipped = !stack.isEmpty();
        long energy = equipped ? ItemMechanicalHeart.energy(stack) : 0L;
        int experiencePerLevel = config.experiencePerLevel.get();
        int maximumLevel = config.maximumLevel.get();
        if (data.needsSync(equipped, energy, experiencePerLevel, maximumLevel)) {
            PacketDistributor.sendToPlayer(player, new MechanicalHeartSyncPayload(equipped, energy, experiencePerLevel, maximumLevel));
            data.update(equipped, energy, experiencePerLevel, maximumLevel);
        }
    }
}
