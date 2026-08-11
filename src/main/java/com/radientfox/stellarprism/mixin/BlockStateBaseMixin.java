package com.radientfox.stellarprism.mixin;

import com.radientfox.stellarprism.ability.Unique.InterloperSkill;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {

    @Inject(
            method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
            at = @At("RETURN"),
            cancellable = true
    )
    private void stellarprism$interloperCollision(
            BlockGetter level,
            BlockPos pos,
            CollisionContext context,
            CallbackInfoReturnable<VoxelShape> cir) {

        VoxelShape originalShape = cir.getReturnValue();

        if (originalShape.isEmpty()) {
            return;
        }

        if (!(context instanceof EntityCollisionContext entityContext)) {
            return;
        }

        Entity entity = entityContext.getEntity();

        if (entity instanceof LivingEntity living &&
                InterloperSkill.PHASING.contains(living.getUUID())) {

            cir.setReturnValue(Shapes.empty());
        }
    }

    @Inject(
            method = "entityInside",
            at = @At("HEAD"),
            cancellable = true
    )
    private void stellarprism$preventEntityInside(
            Level level,
            BlockPos pos,
            Entity entity,
            CallbackInfo ci) {

        if (entity instanceof LivingEntity living &&
                InterloperSkill.PHASING.contains(living.getUUID())) {

            ci.cancel();
        }
    }
}