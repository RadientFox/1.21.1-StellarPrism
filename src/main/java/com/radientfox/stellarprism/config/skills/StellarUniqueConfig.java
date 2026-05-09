package com.radientfox.stellarprism.config.skills;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;
import io.github.manasmods.tensura.config.ability.skill.UniqueSkillConfig;

public class StellarUniqueConfig extends ManasConfig {

    public StellarUniqueConfig.Spinel Spinel = new StellarUniqueConfig.Spinel();


    public StellarUniqueConfig() {
    }

    public String getFileName() {
        return "stellarprism/ability/skill/unique_config";
    }

    public static class Spinel extends ManasSubConfig {
        @Comment("Magicule Acquirement Cost.")
        public double mpAcquirement = 60000.0;
        @Comment("Skill Mastery Points.")
        public double masteryPoints = 60000.0;
        @Comment("Enable In Slot Severance Immunity.")
        public boolean severanceImmunity = true;
        @Comment("Damage multiplier with Spatial Domination.")
        public double spaceDom = 2;
        @Comment("Damage multiplier with Molecular Manipulation.")
        public double molecManip = 2.5;
        @Comment("Damage multiplier with Primordial Domination.")
        public double primodialDom = 3;
        @Comment("Enable Space Transform obtainment.")
        public boolean spaceTransform = true;
        @Comment("Aura cost for Space Cutter.")
        public double apSpaceCutter = 10_000;
        @Comment("Damage for Space Cutter.")
        public double dmgSpaceCutter = 75;
        @Comment("Damage for Space Cutter on mastery.")
        public double masterydmgSpaceCutter = 250;
        @Comment("Aura cost for Pocket Storage.")
        public double apPocketStorage = 1_000;
        @Comment("The number of slots of the Pocket Storage.")
        public int spatialSlots = 54;
        @Comment("The maximum stack size of items in the Pocket Storage.")
        public int spatialStack = 200;
        @Comment("The maximum stack size of items in the Pocket Storage when mastered.")
        public int spatialStackMastered = 500;
        @Comment("Needs Spirit for Spatial Aura.")
        public boolean needsSpirit = true;
        @Comment("Number of battlewills needed for Spatial Aura.")
        public int spaitialBattlewills = 5;

        public Spinel() {
        }
    }







}
