package com.keletu.calamitycurios.event;

import com.keletu.calamitycurios.init.CCItems;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

public final class ClientEvents {
    public static final KeyMapping SPIN_KEY = new KeyMapping("key.calamitycurios.spin", GLFW.GLFW_KEY_V, "key.categories.calamitycurios");
    public static final KeyMapping MECHANICAL_HEART_KEY = new KeyMapping("key.calamitycurios.mechanical_heart", GLFW.GLFW_KEY_H, "key.categories.calamitycurios");

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(CCItems.GIANTSHELL.get(), ResourceLocation.withDefaultNamespace("blocking"), (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
            ItemProperties.register(CCItems.giantTurtleShell.get(), ResourceLocation.withDefaultNamespace("blocking"), (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
        });
    }
}
