package com.radientfox.stellarprism.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.radientfox.stellarprism.Registry.main.skills.StellarUniques;
import com.radientfox.stellarprism.config.skills.StellarUniqueConfig;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.subclass.ISubAbilityModeHolder;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.IAbility;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;


@Mixin(
        value = {SkillUtils.class},
        priority = 918,
        remap = false
)
public abstract class StellarSkillUtilsMixin {

    @ModifyReturnValue(
            method = {"shouldCancelSeverance"},
            remap = false,
            at = {@At("RETURN")}
    )
    private static boolean stellarprism$SeveranceCancel(boolean original, LivingEntity target) {
            if (SkillUtils.hasSkill(target, (ManasSkill)StellarUniques.SPINEL_SKILL.get())) {
                if (StellarUniqueConfig.Spinel.severanceImmunity) {
                    ManasSkillInstance spinel = (ManasSkillInstance) SkillAPI.getSkillsFrom(target).getSkill((ManasSkill) StellarUniques.SPINEL_SKILL.get()).get();
                    if (stellar$isInSlot(target, spinel, 0)) {
                        return true;
                    } else if (stellar$isInSlot(target, spinel, 1)) {
                        return true;
                    } else if (stellar$isInSlot(target, spinel, 2)) {
                        return true;
                    }

                }
            }
            return original;

    }


    @Unique
    private static boolean stellar$isInSlot(LivingEntity entity, ManasSkillInstance instance, int mode) {
        IAbility ability = TensuraStorages.getAbilityFrom(entity);
        ManasSkill parentSkill = instance.getParentSkill();
        if (parentSkill != null) {
            if (!ability.isAbilityInActivePreset(parentSkill)) {
                return false;
            } else {
                Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(entity).getSkill(parentSkill);
                return optional.filter((manasSkillInstance) -> {
                    return ISubAbilityModeHolder.isModeInSlot(manasSkillInstance, entity, instance.getSkill(), mode);
                }).isPresent();
            }
        } else {
            return ability.isAbilityInActivePreset(instance.getSkill(), mode);
        }
    }

}
