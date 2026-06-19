package com.radientfox.stellarprism.ability.extra;

import com.github.hvnbael.trnightmare.util.damage.VoidDamageHelper;
import com.radientfox.stellarprism.Registry.main.StellarEffects;
import com.radientfox.stellarprism.config.skills.StellarUniqueConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class VoidSubordinate extends Skill {


    private static final StellarUniqueConfig.VoidPriestess CONFIG = ConfigRegistry.getConfig(StellarUniqueConfig.class).VoidPriestess;
    public static final ResourceLocation VOID_SUBORDINATE = ResourceLocation.fromNamespaceAndPath("stellarprism", "void_subordinate_skill");

    public VoidSubordinate() {
        super(SkillType.EXTRA);
    }

    public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
        return true;
    }

    public int getModes(ManasSkillInstance instance) {
        return 1;
    }


    public @Nullable ResourceLocation getSkillIcon() {
        return ResourceLocation.fromNamespaceAndPath("stellarprism", "textures/skill/extra/void_subordinate_skill.png");
    }

    public String getModeId(ManasSkillInstance instance, int mode) {
        String var10000;
        switch (mode) {
            default -> var10000 = super.getModeId(instance, mode);
        }

        return var10000;
    }

    public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity attacker, LivingEntity target, DamageSource source, Changeable<Float> amount) {
        if (source.getDirectEntity() != attacker) {
            return true;
        } else if (!VoidDamageHelper.isVoidDamage(source)) {
            return true;
        } else {
            if (attacker.hasEffect(StellarEffects.VOID_SUBORDINATE)) {
                MobEffectInstance current = attacker.getEffect(StellarEffects.VOID_SUBORDINATE);
                assert current != null;
                int effectLvl = (current.getAmplifier());

                float damage = (float) ((instance.isMastered(attacker) ? CONFIG.increaseAlliesMastered : CONFIG.increaseAllies) * effectLvl);
                VoidDamageHelper.dealVoidDamage(target, (LivingEntity) attacker, damage);

            }
            return true;

        }

    }



    }
