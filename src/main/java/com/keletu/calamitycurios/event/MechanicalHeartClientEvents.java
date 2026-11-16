package com.keletu.calamitycurios.event;

import com.keletu.calamitycurios.CalamityCurios;
import com.keletu.calamitycurios.init.CCAttachments;
import com.keletu.calamitycurios.packet.MechanicalHeartActivatePayload;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = CalamityCurios.MODID, value = Dist.CLIENT)
public final class MechanicalHeartClientEvents {

    private static final ResourceLocation HEART_BAR = ResourceLocation.fromNamespaceAndPath(CalamityCurios.MODID, "textures/gui/heart_bar.png");
    private static final ResourceLocation EXPERIENCE_BACKGROUND = ResourceLocation.withDefaultNamespace("hud/experience_bar_background");
    private static final ResourceLocation EXPERIENCE_PROGRESS = ResourceLocation.withDefaultNamespace("hud/experience_bar_progress");
    private static final int BAR_WIDTH = 182;

    private MechanicalHeartClientEvents() {
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        while (ClientEvents.MECHANICAL_HEART_KEY.consumeClick()) {
            if (minecraft.screen == null && minecraft.player != null && minecraft.player.isAlive() && !MechanicalHeartEvents.findEquipped(minecraft.player).isEmpty()) {
                PacketDistributor.sendToServer(new MechanicalHeartActivatePayload());
            }
        }
    }

    @SubscribeEvent
    public static void onGuiLayer(RenderGuiLayerEvent.Pre event) {
        boolean bar = event.getName().equals(VanillaGuiLayers.EXPERIENCE_BAR);
        if (!bar && !event.getName().equals(VanillaGuiLayers.EXPERIENCE_LEVEL)) {
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null || minecraft.gameMode == null || minecraft.options.hideGui || !minecraft.gameMode.hasExperience() || player.jumpableVehicle() != null || !player.getData(CCAttachments.MECHANICAL_HEART).equipped()) {
            return;
        }
        event.setCanceled(true);
        if (bar) {
            render(event.getGuiGraphics(), player, minecraft.font);
        }
    }

    private static void render(GuiGraphics graphics, Player player, Font font) {
        var data = player.getData(CCAttachments.MECHANICAL_HEART);
        int x = graphics.guiWidth() / 2 - 91;
        int y = graphics.guiHeight() - 29;
        int fill = Mth.clamp(Math.round(data.progress() * BAR_WIDTH), 0, BAR_WIDTH);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        graphics.blit(HEART_BAR, x - 5, y - 5, 0, 0, 187, 12, 256, 256);
        if (fill > 0) {
            graphics.blit(HEART_BAR, x, y, 5, 26, fill, 5, 256, 256);
        }
        graphics.blit(HEART_BAR, x - 5, y - 5, 0, 0, 24, 12, 256, 256);

        if (player.experienceLevel > 0 || player.experienceProgress > 0) {
            renderPlayerExperience(graphics, player, font, x + BAR_WIDTH + 5, y);
        }
        RenderSystem.disableBlend();

        drawLevel(graphics, font, Integer.toString(data.level()), graphics.guiWidth() / 2, y - 6, 0xE879C7);
    }

    private static void renderPlayerExperience(GuiGraphics graphics, Player player, Font font, int x, int y) {
        int width = Math.min(52, graphics.guiWidth() - x - 5);
        if (width < 20) {
            return;
        }
        int fill = Mth.clamp(Math.round(player.experienceProgress * width), 0, width);
        graphics.blitSprite(EXPERIENCE_BACKGROUND, x, y, width, 5);
        if (fill > 0) {
            graphics.blitSprite(EXPERIENCE_PROGRESS, width, 5, 0, 0, x, y, fill, 5);
        }
        graphics.blit(HEART_BAR, x - 5, y - 5, 0, 12, 24, 12, 256, 256);
        if (player.experienceLevel > 0) {
            drawLevel(graphics, font, Integer.toString(player.experienceLevel), x + width / 2, y - 6, 0x80FF20);
        }
    }

    private static void drawLevel(GuiGraphics graphics, Font font, String level, int centerX, int y, int color) {
        int x = centerX - font.width(level) / 2;
        graphics.drawString(font, level, x + 1, y, 0, false);
        graphics.drawString(font, level, x - 1, y, 0, false);
        graphics.drawString(font, level, x, y + 1, 0, false);
        graphics.drawString(font, level, x, y - 1, 0, false);
        graphics.drawString(font, level, x, y, color, false);
    }
}
