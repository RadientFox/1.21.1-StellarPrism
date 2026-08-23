package com.radientfox.stellarprism.item;

import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class StellarFoodProperties {


    public static final FoodProperties PRISM_SHARD = createEssenceProperties();
    public static final FoodProperties CORRUPTION_SHARD = createEssenceProperties();
    public static final FoodProperties VILLAINOUS_SPIRIT = createEssenceProperties();
    public static final FoodProperties HEROES_SPIRIT = createEssenceProperties();
    public static final FoodProperties HERO_SHARD = createEssenceProperties();
    public static final FoodProperties TIME_ESSENCE = createEssenceProperties();


    public StellarFoodProperties() {
    }



    private static FoodProperties createEssenceProperties() {
        return (new FoodProperties.Builder()).alwaysEdible().nutrition(4).saturationModifier(0.3F).build();
    }

    }
