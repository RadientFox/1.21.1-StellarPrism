package com.radientfox.stellarprism.item;

import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class StellarFoodProperties {



    public static final FoodProperties PRISM_SHARD;

    public StellarFoodProperties() {
    }

    static {
        PRISM_SHARD = (new FoodProperties.Builder()).nutrition(0).saturationModifier(0.0F).effect(new MobEffectInstance(MobEffects.WITHER, 100, 1), 1.0F).effect(new MobEffectInstance(MobEffects.POISON, 100, 1), 1.0F).alwaysEdible().build();

    }

    }
