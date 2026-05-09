package com.radientfox.stellarprism.ability.Unique;

import com.radientfox.stellarprism.config.skills.StellarUniqueConfig;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import net.minecraft.world.entity.LivingEntity;

public class Spinel extends Skill {
    private static StellarUniqueConfig.Spinel CONFIG;

    public Spinel() {
        super(SkillType.UNIQUE);
    }

    public double getDefaultAcquiringMagiculeCost() {
        return CONFIG.mpAcquirement;
    }

    public int getModes(ManasSkillInstance instance) {
        return 2;
    }

    public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
        return mode == 0 ? 1 : 0;
    }

    public String getModeId(ManasSkillInstance instance, int mode) {
        String var10000;
        switch (mode) {
            case 0 -> var10000 = "absolute_severance.coat";
            case 1 -> var10000 = "absolute_severance.projectile";
            default -> var10000 = super.getModeId(instance, mode);
        }

        return var10000;
    }

    public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
        double var10000;
        switch (mode) {
         //   case 0 -> var10000 = CONFIG.magiculeCostCoating;
         //   case 1 -> var10000 = CONFIG.magiculeCostProjectile;
            default -> var10000 = 0.0;
        }

        return var10000;
    }

}
