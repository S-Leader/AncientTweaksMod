package com.keletu.ancienttweaks.event;

import com.keletu.ancienttweaks.AncientTweaks;
import com.keletu.ancienttweaks.baubles.client.*;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(
        modid = AncientTweaks.MODID,
        value = Dist.CLIENT
)
public final class AncientTweaksClientRenderEvents {

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        //event.registerLayerDefinition(
        //        ModelJelly.LAYER_LOCATION,
        //        ModelJelly::createBodyLayer
        //);

        event.registerLayerDefinition(
                ModelBubbleShield.LAYER_LOCATION,
                ModelBubbleShield::createBodyLayer
        );

        event.registerLayerDefinition(
                ModelAbsorber.LAYER_LOCATION,
                ModelAbsorber::createBodyLayer
        );
    }

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        for (PlayerSkin.Model skinName : event.getSkins()) {
            PlayerRenderer renderer = event.getSkin(skinName);

            if (renderer == null) {
                continue;
            }

            //ModelJelly jellyModel = new ModelJelly(
            //        event.getEntityModels().bakeLayer(ModelJelly.LAYER_LOCATION)
            //);
//
            //renderer.addLayer(new LayerBeltJelly(renderer, jellyModel));

            ModelBubbleShield spongeShield = new ModelBubbleShield(
                    event.getEntityModels().bakeLayer(ModelBubbleShield.LAYER_LOCATION)
            );

            renderer.addLayer(new LayerBubbleShield(renderer, spongeShield));

            ModelAbsorber absorberModel = new ModelAbsorber(
                    event.getEntityModels().bakeLayer(ModelAbsorber.LAYER_LOCATION)
            );

            renderer.addLayer(new LayerTheAbsorber(renderer, absorberModel));

            renderer.addLayer(new LayerSlimeHead(renderer));
            renderer.addLayer(new LayerShieldBack(renderer));
            renderer.addLayer(new LayerCrabGlove(renderer));
        }
    }
}
