package com.radientfox.stellarprism.entities.bosses;

import com.github.hvnbael.trnightmare.main.entity.sentientboss.AbstractTrNightmareSentientBossEntity;
import com.github.hvnbael.trnightmare.main.entity.sentientboss.SentientBossDefinition;
import io.github.manasmods.tensura.entity.template.PlayerLikeEntity;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class HerobrianeSentientBoss extends AbstractTrNightmareSentientBossEntity {

    private static final SentientBossDefinition DEF = new SentientBossDefinition(
            "Herobrine",
            "hostile",
            5_000.0D,
            100_000.0D,
            20.0D,
            6_000_000.0D,
            8_000_000.0D,
            List.of(
                    "tensura:ultraspeed_regeneration",
                    "tensura:self_regeneration",
                    "tensura:infinite_regeneration",
                    "tensura:spatial_manipulation",
                    "tensura:shadow_striker",
                    "tensura:law_manipulation",
                    "tensura:multilayer_barrier"
            ),
            List.of("resistance", "nullification", "manipulation"),
            true,
            "minecraft:diamond_sword",
            70.0D,
            0.3D,
            96.0D
    );

    public HerobrianeSentientBoss(EntityType<? extends PlayerLikeEntity> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return baseAttributes(
                DEF.maxHealth(),
                DEF.armor(),
                DEF.attackDamage(),
                DEF.movementSpeed(),
                DEF.followRange()
        )

                .add(TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION, 10.0D)
                .add(TensuraAttributes.AUTO_MELEE_DODGE_CHANCE, 50.0D)
                .add(TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE, 50.0D)
                .add(TensuraAttributes.DODGE_NEGATE_CHANCE, 75.0D)
                .add(TensuraAttributes.CHANT_SPEED, 3.0D)
                .add(TensuraAttributes.PRESENCE_SENSE, 155.0D);


    }

    @Override
    protected SentientBossDefinition definition() {
        return DEF;
    }

    @Override
    protected String texturePathUnderHumanoids() {
        return "textures/humanoids/herobrine.png";
    }

}
