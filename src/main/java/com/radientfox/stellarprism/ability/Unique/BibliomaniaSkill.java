package com.radientfox.stellarprism.ability.Unique;

import io.github.manasmods.tensura.ability.skill.Skill;

public class BibliomaniaSkill extends Skill {

    public BibliomaniaSkill() {
        super(Skill.SkillType.UNIQUE);
    }

    @Override
    public int getMaxMastery() {
        return 5000;
    }


}
