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

public class SpatialAuraEffect extends TensuraMobEffect {
        protected static final ResourceLocation SpaceAura = ResourceLocation.fromNamespaceAndPath("stellarprism", "spaceaura");

    public SpatialAuraEffect() {
            super(MobEffectCategory.BENEFICIAL, (new Color(255, 181, 0, 255)).getRGB());
        }
    public SpatialAuraEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    public void onEffectRemoved(LivingEntity entity, MobEffectInstance instance) {
            if (entity instanceof Player) {
                Player player = (Player) entity;
                player.displayClientMessage(Component.translatable("stellarprism.skill.mode.heartstone.deactive").setStyle(Style.EMPTY.withColor(ChatFormatting.LIGHT_PURPLE)), false);
            }

        }

}
