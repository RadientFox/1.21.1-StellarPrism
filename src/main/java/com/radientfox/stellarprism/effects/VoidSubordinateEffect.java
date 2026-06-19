package com.radientfox.stellarprism.effects;

import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;

import java.awt.*;

public class VoidSubordinateEffect extends TensuraMobEffect {
    protected static final ResourceLocation VOID_SUBORDINATE = ResourceLocation.fromNamespaceAndPath("stellarprism", "void_subordinate");

    public VoidSubordinateEffect() {
        super(MobEffectCategory.BENEFICIAL, (new Color(255, 181, 0, 255)).getRGB());
    }



}
