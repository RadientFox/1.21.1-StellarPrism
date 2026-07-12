package com.radientfox.stellarprism.ability.Unique;

import com.radientfox.stellarprism.entities.bosses.HerobrianeSentientBoss;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.entity.projectile.magic.SpaceCutProjectile;
import io.github.manasmods.tensura.entity.template.PlayerLikeEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class TestingSkill extends Skill {


    public TestingSkill() {
        super(SkillType.ULTIMATE);
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


    public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {

        switch (mode) {
            case 0:

        }
    }
}
