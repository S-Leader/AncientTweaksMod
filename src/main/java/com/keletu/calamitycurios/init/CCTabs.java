package com.keletu.calamitycurios.init;

import com.keletu.calamitycurios.CalamityCurios;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CCTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CalamityCurios.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB = TABS.register("pvz_myh",
            () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                    .icon(() -> new ItemStack(CCItems.theAbsorber.get()))
                    .title(Component.translatable("creativetab.calamitycurios"))
                    .displayItems((params, output) -> {
                        CCItems.ITEMS.getEntries().forEach(ro -> output.accept(ro.get()));
                    })
                    .build()
    );
}