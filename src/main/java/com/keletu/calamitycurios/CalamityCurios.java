package com.keletu.calamitycurios;

import com.keletu.calamitycurios.event.ClientEvents;
import com.keletu.calamitycurios.init.*;
import com.keletu.calamitycurios.packet.ModNetwork;
import com.keletu.calamitycurios.util.CCDataGen;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.slf4j.Logger;

@Mod(CalamityCurios.MODID)
public class CalamityCurios {
    public static final String MODID = "calamitycurios";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CalamityCurios(IEventBus modEventBus, ModContainer container) {
        CCArmorMats.ARMOR_MATERIALS.register(modEventBus);
        CCItems.ITEMS.register(modEventBus);
        CCEffects.EFFECTS.register(modEventBus);
        CCLoots.LOOTS.register(modEventBus);
        CCSounds.SOUNDS.register(modEventBus);
        CCTabs.TABS.register(modEventBus);
        CCAttachments.ATTACHMENT_TYPES.register(modEventBus);
        CCDataComponents.COMPONENTS.register(modEventBus);

        modEventBus.addListener(ModNetwork::register);
        modEventBus.addListener(CCDataGen::gatherData);

        container.registerConfig(ModConfig.Type.COMMON, CalamityCuriosConfig.COMMON_SPEC);
        container.registerConfig(ModConfig.Type.SERVER, CalamityCuriosConfig.MECHANICAL_HEART_SPEC);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(ClientEvents::onClientSetup);
            modEventBus.addListener(RegisterKeyMappingsEvent.class, event -> {
                event.register(ClientEvents.SPIN_KEY);
                event.register(ClientEvents.MECHANICAL_HEART_KEY);
            });
        }
    }
}
