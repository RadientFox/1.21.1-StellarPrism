package com.radientfox.stellarprism.effects;
/*
import com.radientfox.stellarprism.item.strengthener.FlameStrengthen;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.awt.*;

public class ElementalStrengthen extends MobEffect {

    protected static final ResourceLocation ELEMENTAL_BUFF = ResourceLocation.fromNamespaceAndPath("stellarprism", "elemental_strnegthen");

    public ElementalStrengthen() {

        super(MobEffectCategory.BENEFICIAL, (new Color(255, 181, 0, 255)).getRGB());



    }


    private ResourceKey<Attribute> getAtribute(LivingEntity attacker, FlameStrengthen instance) {
        CompoundTag tag = instance.();
        int element = tag.getInt("element");
        ResourceKey var10000;
        switch (element) {
            case 1 -> var10000 = (ResourceKey) TensuraAttributes.FLAME_BOOST;
            case 2 -> var10000 = (ResourceKey) TensuraAttributes.EARTH_BOOST;
            case 3 -> var10000 = (ResourceKey) TensuraAttributes.WIND_BOOST;
            case 4 -> var10000 = (ResourceKey) TensuraAttributes.WATER_BOOST;
            case 5 -> var10000 = (ResourceKey) TensuraAttributes.SPACE_BOOST;
            case 6 -> var10000 = (ResourceKey) TensuraAttributes.LIGHT_BOOST;
            default -> var10000 = (ResourceKey) TensuraAttributes.ILLUSION_BOOST;
        }

        ResourceKey<Attribute> elemental = var10000;


        return elemental;
    }



    public void onEffectRemoved(LivingEntity entity, MobEffectInstance instance) {
        if (entity instanceof Player) {
            Player player = (Player) entity;


        }

    }
}


 */