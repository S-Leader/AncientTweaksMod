package com.keletu.ancienttweaks;

import com.keletu.ancienttweaks.event.ClientEvents;
import com.keletu.ancienttweaks.init.*;
import com.keletu.ancienttweaks.packet.ModNetwork;
import com.keletu.ancienttweaks.util.ATDataGen;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(AncientTweaks.MODID)
public class AncientTweaks {
    public static final String MODID = "ancienttweaks";
    public static final Logger LOGGER = LogUtils.getLogger();

    public AncientTweaks(IEventBus modEventBus, ModContainer container) {
        ATArmorMats.ARMOR_MATERIALS.register(modEventBus);
        ATItems.ITEMS.register(modEventBus);
        ATEffects.EFFECTS.register(modEventBus);
        ATLoots.LOOTS.register(modEventBus);
        ATTabs.TABS.register(modEventBus);
        ATAttachments.ATTACHMENT_TYPES.register(modEventBus);

        modEventBus.addListener(ModNetwork::register);
        modEventBus.addListener(ATDataGen::gatherData);

        container.registerConfig(ModConfig.Type.COMMON, AncientTweaksConfig.COMMON_SPEC);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(ClientEvents::onClientSetup);
            modEventBus.addListener(ClientEvents::registerLayer);
        }
    }
}
