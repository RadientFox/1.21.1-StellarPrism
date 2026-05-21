package com.radientfox.stellarprism.mixin;

import com.github.hvnbael.trnightmare.compat.tensura.TensuraSkillCompat;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.radientfox.stellarprism.Regestrys.main.skills.StellarUniques;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({SkillUtils.class})
public class SkillUtilsMixin {


}
