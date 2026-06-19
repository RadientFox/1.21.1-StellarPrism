package com.radientfox.stellarprism.compat;

import com.radientfox.stellarprism.Registry.main.skills.StellarUniques;
import io.github.manasmods.tensura.ability.SkillUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public final class SeveranceCompat {

    public static boolean shouldCancelSeverance(LivingEntity target, @Nullable DamageSource source) {

        if (SkillUtils.shouldCancelSeverance(target, source)) {
            return true;
        }
        return SkillUtils.isSkillMastered(target, StellarUniques.SPINEL_SKILL.get());
    }
}