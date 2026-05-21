package com.radientfox.stellarprism.compat;

import com.github.hvnbael.trnightmare.compat.tensura.TensuraSkillCompat;
import com.radientfox.stellarprism.Regestrys.main.skills.StellarUniques;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.storage.TensuraStorages;
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