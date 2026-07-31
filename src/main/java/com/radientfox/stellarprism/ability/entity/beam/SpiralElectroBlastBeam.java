package com.radientfox.stellarprism.ability.entity.beam;

import com.radientfox.stellarprism.Registry.main.StellarEntityTypes;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.magic.beam.BeamProjectile;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

import java.awt.*;

public class SpiralElectroBlastBeam extends BeamProjectile {

    public SpiralElectroBlastBeam(EntityType<? extends BeamProjectile> type, Level level) {
        super(type, level);

        this.setElementalAttack(true);

        this.beamColorAndSize.put(new Color(2, 218, 0, 200), 0.2F);
        this.beamColorAndSize.put(new Color(25, 246, 0, 100), 0.4F);
        this.beamColorAndSize.put(new Color(0, 255, 127, 30), 0.6F);
    }

    public SpiralElectroBlastBeam(Level level, LivingEntity shooter) {
        this(StellarEntityTypes.SPIRAL_ELECTRO_BLAST.get(), level);
        this.setOwner(shooter);
    }


    @Override
    public ResourceLocation[] getTextureLocation() {
        return new ResourceLocation[]{ResourceLocation.fromNamespaceAndPath("tensura", "textures/entity/projectiles/beam/electric_beam.png")};
    }


    @Override
    public ResourceKey<net.minecraft.world.damagesource.DamageType> getDamageType() {
        return TensuraDamageTypes.LIGHTNING_ELEMENTAL;
    }


    @Override
    protected boolean dealDamage(Entity target) {

        if (super.dealDamage(target)) {

            TensuraParticleHelper.addServerParticlesAroundSelf(target, TensuraParticleTypes.LIGHTNING_EFFECT.get());

            if (target instanceof LivingEntity living) {

                // Apply Cook here
                applyCook(living);
            }

            return true;
        }

        return false;
    }


    private void applyCook(LivingEntity target) {
        AttributeInstance health = target.getAttribute(Attributes.MAX_HEALTH);

        if (health == null) {
            return;
        }

        ResourceLocation COOK = ResourceLocation.fromNamespaceAndPath("tensura", "cook");

        double reduction = target.getMaxHealth() * -0.1D; // 10% max HP reduction

        AttributeModifier modifier = new AttributeModifier(COOK, reduction, AttributeModifier.Operation.ADD_VALUE);

        health.removeModifier(COOK);
        health.addOrReplacePermanentModifier(modifier);
    }
}