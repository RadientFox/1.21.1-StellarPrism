package com.radientfox.stellarprism.mixin;

import com.radientfox.stellarprism.races.Fox.Elemental.IKitsuenElement;
import com.radientfox.stellarprism.races.Fox.Elemental.KitsuneElement;
import com.radientfox.stellarprism.storages.KitsuneElementStorage;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.storage.api.StorageKey;
import io.github.manasmods.tensura.client.screen.MainScreen;
import io.github.manasmods.tensura.client.screen.templates.SimpleScreen;
import io.github.manasmods.tensura.util.client.RenderHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.network.chat.Component;


import java.awt.*;
import java.util.Optional;

@Mixin({MainScreen.class})

public class BaseScreenMixin extends SimpleScreen {
    protected BaseScreenMixin(Component title, int width, int height) {
            super((net.minecraft.network.chat.Component) title, width, height);
        }

        @Inject(
                method = {"renderRaceStats(Lnet/minecraft/client/gui/GuiGraphics;II)V"},
                at = {@At("TAIL")},
                cancellable = true
        )
        private void StellarElements(GuiGraphics graphics, int mX, int mY, CallbackInfo ci) {
            Optional<ManasRaceInstance> race = RaceAPI.getRaceFrom(this.player).getRace();
            if (!race.isEmpty()) {

                Player player = this.player;


                String colorText;
                KitsuneElement element = KitsuneElement.getElement(player);

                if (element != null) {
                    String elementText = Component.translatable("mysticism.menu.element").getString();
                    if (element != null) {
                        colorText = element.getName().getString();
                        RenderHelper.drawTextWithTooltip(graphics, this.font, elementText, guiLeft + 95, guiTop + 120, Color.WHITE.getRGB(), mX, mY, Component.empty(), this);
                        RenderHelper.drawTextWithTooltip(graphics, this.font, colorText, guiLeft + 140, guiTop + 120, element.getColor(), mX, mY, Component.empty(), this);
                    }
                }




            }



        }




}
