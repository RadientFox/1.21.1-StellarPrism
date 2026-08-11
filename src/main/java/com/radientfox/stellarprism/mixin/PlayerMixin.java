package com.radientfox.stellarprism.mixin;

import com.radientfox.stellarprism.ability.Unique.InterloperSkill;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Inject(
            method = "tick",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/entity/Entity;noPhysics:Z",
                    opcode = org.objectweb.asm.Opcodes.PUTFIELD,
                    shift = At.Shift.AFTER
            )
    )
    private void stellarprism$interloperNoClip(CallbackInfo ci) {
        Player player = (Player)(Object)this;

        if (InterloperSkill.PHASING.contains(player.getUUID())) {
            player.noPhysics = true;
            player.setOnGround(false);
        }
    }
}