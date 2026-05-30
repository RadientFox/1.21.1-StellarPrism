package com.radientfox.stellarprism.effects;

import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.awt.*;

public class WindAuraEffect extends TensuraMobEffect {
        protected static final ResourceLocation SpaceAura = ResourceLocation.fromNamespaceAndPath("stellarprism", "windaura");

    public WindAuraEffect() {
            super(MobEffectCategory.BENEFICIAL, (new Color(0, 255, 0, 1)).getRGB());
        }
    public WindAuraEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    public void onEffectRemoved(LivingEntity entity, MobEffectInstance instance) {
            if (entity instanceof Player) {
                Player player = (Player) entity;
                player.displayClientMessage(Component.translatable("stellarprism.skill.mode.heartstone.deactive").setStyle(Style.EMPTY.withColor(ChatFormatting.LIGHT_PURPLE)), false);
            }

        }

}
