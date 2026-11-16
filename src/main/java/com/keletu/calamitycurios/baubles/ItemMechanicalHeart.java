package com.keletu.calamitycurios.baubles;

import com.keletu.calamitycurios.CalamityCuriosConfig;
import com.keletu.calamitycurios.init.CCDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public final class ItemMechanicalHeart extends Item implements ICurioItem {

    public ItemMechanicalHeart(Properties properties) {
        super(properties);
    }

    public static long energy(ItemStack stack) {
        return Math.max(0L, stack.getOrDefault(CCDataComponents.MECHANICAL_HEART_ENERGY.get(), 0L));
    }

    public static void setEnergy(ItemStack stack, long energy) {
        stack.set(CCDataComponents.MECHANICAL_HEART_ENERGY.get(), Math.max(0L, energy));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        var config = CalamityCuriosConfig.CONFIG_MECHANICAL_HEART;
        int experiencePerLevel = config.experiencePerLevel.get();
        int maximum = config.maximumLevel.get();
        long energy = energy(stack);
        long level = Math.min(energy / experiencePerLevel, maximum);
        tooltip.add(Component.translatable("tooltip.calamitycurios.mechanical_heart.charge", experiencePerLevel).withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.add(Component.translatable("tooltip.calamitycurios.mechanical_heart.activate", Component.keybind("key.calamitycurios.mechanical_heart"), config.levelsPerUse.get()).withStyle(ChatFormatting.DARK_PURPLE));
    }
}
