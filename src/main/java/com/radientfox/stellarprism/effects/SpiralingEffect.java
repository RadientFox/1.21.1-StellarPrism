package com.radientfox.stellarprism.effects;

import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

import java.awt.*;
import java.util.UUID;

public class SpiralingEffect extends TensuraMobEffect {

    public static final String SPIRALING = "stellarprism:spiraling";

    private static final ResourceLocation MAGIC_COST_ID =
            ResourceLocation.fromNamespaceAndPath("stellarprism", "spiraling_magic_cost");

    public SpiralingEffect() {
        super(MobEffectCategory.HARMFUL, new Color(68, 255, 0).getRGB());

        this.addAttributeModifier(TensuraAttributes.MAGIC_COST_MULTIPLIER, MAGIC_COST_ID, 0.30D, Operation.ADD_VALUE);
    }
}
