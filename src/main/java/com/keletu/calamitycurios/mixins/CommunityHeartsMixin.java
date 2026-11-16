package com.keletu.calamitycurios.mixins;

import com.keletu.calamitycurios.baubles.client.CommunityHeartRenderer;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;

@Mixin(Gui.class)
public abstract class CommunityHeartsMixin {

    @WrapOperation(method = "renderHearts", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIZZZ)V"), require = 4)
    private void rainbowBonusHealth(Gui gui, GuiGraphics graphics, @Coerce Object type, int x, int y, boolean hardcore, boolean blinking, boolean half, Operation<Void> original, @Local(argsOnly = true) Player player, @Local(index = 17) int heartIndex) {
        original.call(gui, graphics, type, x, y, hardcore, blinking, half);
        CommunityHeartRenderer.render(graphics, player, type, x, y, heartIndex, blinking, half);
    }
}
