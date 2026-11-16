package com.keletu.calamitycurios.baubles.client;

import com.keletu.calamitycurios.baubles.community.CommunityBonuses;
import com.keletu.calamitycurios.event.TheCommunityEvents;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public final class CommunityHeartRenderer {

    // Match the vanilla 9x9 heart interior; the original sprite supplies its outline.
    private static final String[] PIXELS = {".........", "..cc.cc..", ".chccccc.", ".ccccccc.", ".scccccs.", "..scccs..", "...scs...", "....s....", "........."};

    private CommunityHeartRenderer() {
    }

    public static void render(GuiGraphics graphics, Player player, Object heartType, int x, int y, int heartIndex, boolean blinking, boolean half) {
        if (!(heartType instanceof Enum<?> type) || !type.name().equals("NORMAL")) {
            return;
        }
        AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
        AttributeModifier modifier = attribute == null ? null : attribute.getModifier(TheCommunityEvents.MODIFIER_ID);
        if (modifier == null || modifier.amount() <= 0) {
            return;
        }

        int firstBonusPoint = Mth.ceil(player.getMaxHealth() - modifier.amount());
        int maximumPoint = Mth.ceil(player.getMaxHealth());
        float phase = (Util.getMillis() % 8000L) / 8000.0F;
        for (int py = 1; py < 8; py++) {
            for (int px = 1; px < 8; px++) {
                char pixel = PIXELS[py].charAt(px);
                if (pixel == '.') {
                    continue;
                }
                int side = px < 5 ? 0 : 1;
                int point = heartIndex * 2 + side;
                if ((half && side == 1) || !CommunityBonuses.isBonusHealthPoint(point, firstBonusPoint, maximumPoint)) {
                    continue;
                }
                float hue = (phase + heartIndex * 0.075F + px * 0.015F) % 1.0F;
                float saturation = pixel == 'h' || blinking ? 0.25F : 0.8F;
                float brightness = pixel == 's' ? 0.68F : 1.0F;
                int color = 0xFF000000 | Mth.hsvToRgb(hue, saturation, brightness);
                graphics.fill(x + px, y + py, x + px + 1, y + py + 1, color);
            }
        }
    }
}
