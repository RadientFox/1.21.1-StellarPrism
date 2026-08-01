package com.radientfox.stellarprism.ability.entity.projectile;

import com.radientfox.stellarprism.Registry.main.StellarEffects;

import io.github.manasmods.tensura.entity.projectile.ThrownItemProjectile;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class SpiralHeartProjectile extends ThrownItemProjectile {

    public SpiralHeartProjectile(Level level, LivingEntity owner, ItemStack stack, boolean canBreakBlocks, float damage) {
        super(level, owner, stack, canBreakBlocks, damage);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        if (result.getEntity() instanceof LivingEntity target) {
            target.addEffect(new MobEffectInstance(
                    StellarEffects.SPIRALING,
                    200,
                    0
            ));
        }
    }
}