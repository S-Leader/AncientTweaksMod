package com.keletu.calamitycurios.baubles;

import com.keletu.calamitycurios.event.TheCommunityEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public final class ItemTheCommunity extends Item implements ICurioItem {

    public ItemTheCommunity(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

    @Override
    public boolean canEquip(SlotContext context, ItemStack stack) {
        if (!(context.entity() instanceof Player player)) {
            return false;
        }
        return CuriosApi.getCuriosInventory(player).map(handler -> handler.findCurios(this).stream().allMatch(result -> result.slotContext().identifier().equals(context.identifier()) && result.slotContext().index() == context.index())).orElse(false);
    }

    @Override
    public void onUnequip(SlotContext context, ItemStack newStack, ItemStack stack) {
        if (context.entity() instanceof ServerPlayer player) {
            TheCommunityEvents.removeBonuses(player);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.calamitycurios.the_community.flavor").withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.add(Component.translatable("tooltip.calamitycurios.the_community.unique").withStyle(ChatFormatting.DARK_PURPLE));
    }
}
