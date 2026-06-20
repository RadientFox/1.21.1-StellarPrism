package com.radientfox.stellarprism.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

@SuppressWarnings("unused")
public class StellarUtilsM {

    public static Entity getTargetEntity(Player player, double range) {
        Level level = player.level();
        Vec3 eyePos = player.getEyePosition();
        Vec3 viewVec = player.getViewVector(1.0F);
        Vec3 targetVec = eyePos.add(viewVec.x * range, viewVec.y * range, viewVec.z * range);

        AABB searchArea = player.getBoundingBox().expandTowards(viewVec.scale(range)).inflate(1.0D, 1.0D, 1.0D);
        EntityHitResult hitResult = ProjectileUtil.getEntityHitResult(
                player,
                eyePos,
                targetVec,
                searchArea,
                (entity) -> !entity.isSpectator() && entity.isPickable() && entity instanceof LivingEntity,
                range * range
        );

        return hitResult != null ? hitResult.getEntity() : null;
    }
}
