package com.radientfox.stellarprism.effects;

import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

import java.awt.Color;

public class TengenToppaEffect extends TensuraMobEffect {

    public TengenToppaEffect() {
        super(MobEffectCategory.BENEFICIAL, new Color(40, 255, 44).getRGB());

        addAttributeModifier(
                Attributes.ATTACK_DAMAGE,
                ResourceLocation.fromNamespaceAndPath("stellarprism", "tengen_toppa_attack"),
                25.0,
                Operation.ADD_VALUE);

        addAttributeModifier(
                Attributes.MAX_HEALTH,
                ResourceLocation.fromNamespaceAndPath("stellarprism", "tengen_toppa_health"),
                2000.0,
                Operation.ADD_VALUE);

        addAttributeModifier(
                TensuraAttributes.MAX_SPIRITUAL_HEALTH,
                ResourceLocation.fromNamespaceAndPath("stellarprism", "tengen_toppa_health"),
                100.0,
                Operation.ADD_MULTIPLIED_TOTAL);

        addAttributeModifier(
                TensuraAttributes.MAX_MAGICULE,
                ResourceLocation.fromNamespaceAndPath("stellarprism", "tengen_toppa_energy"),
                1.0,
                Operation.ADD_MULTIPLIED_TOTAL);

        addAttributeModifier(
                TensuraAttributes.MAX_AURA,
                ResourceLocation.fromNamespaceAndPath("stellarprism", "tengen_toppa_energy"),
                1.0,
                Operation.ADD_MULTIPLIED_TOTAL);
    }
}