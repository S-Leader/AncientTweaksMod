package com.keletu.ancienttweaks.baubles;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.AncientTweaksConfig;
import com.keletu.ancienttweaks.init.ATItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class ItemTheAbsorber extends Item implements ICurioItem {

    public static final String TAG_DURATION = "uses";

    public ItemTheAbsorber(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();

        if (!(entity instanceof Player player)) {
            return false;
        }

        return CuriosApi.getCuriosInventory(player).map(handler -> handler.findFirstCurio(ATItems.theAbsorber.get()).isEmpty()).orElse(true);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();

        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "movement_speed"), AncientTweaksConfig.CONFIG_CALAMITY.greenJellySpeed.get(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        modifiers.put(Attributes.ARMOR, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(AncientTweaks.MODID, "armor_bonus"), AncientTweaksConfig.CONFIG_CALAMITY.theAbsorberShellArmor.get(), AttributeModifier.Operation.ADD_VALUE));

        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Item.TooltipContext level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.empty());

        tooltip.add(Component.translatable("tooltip.ancienttweaks.theAbsorber1", Math.round(AncientTweaksConfig.CONFIG_CALAMITY.greenJellySpeed.get() * 100)).withStyle(ChatFormatting.DARK_PURPLE));

        tooltip.add(Component.translatable("tooltip.ancienttweaks.theAbsorber2").withStyle(ChatFormatting.DARK_PURPLE));

        tooltip.add(Component.translatable("tooltip.ancienttweaks.theAbsorber3").withStyle(ChatFormatting.DARK_PURPLE));

        tooltip.add(Component.translatable("tooltip.ancienttweaks.theAbsorber4").withStyle(ChatFormatting.DARK_PURPLE));

        tooltip.add(Component.translatable("tooltip.ancienttweaks.theAbsorber5").withStyle(ChatFormatting.DARK_PURPLE));

        tooltip.add(Component.translatable("tooltip.ancienttweaks.theAbsorber6").withStyle(ChatFormatting.DARK_PURPLE));

        tooltip.add(Component.translatable("tooltip.ancienttweaks.theAbsorber7", Math.round(AncientTweaksConfig.CONFIG_CALAMITY.theAbsorberAbsorb.get() * 100)).withStyle(ChatFormatting.DARK_PURPLE));
    }
}