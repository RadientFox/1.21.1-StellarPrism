package com.radientfox.stellarprism.config.skills;

import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;

import java.util.List;

public class StellarIntrinsicsConfig extends ManasConfig {

    public static StellarIntrinsicsConfig.KitsuneIllusion KitsuneIllusion = new StellarIntrinsicsConfig.KitsuneIllusion();


    public StellarIntrinsicsConfig() {
    }

    public String getFileName() {
        return "stellarprism/ability/skill/intrinsic_config";
    }


    public static class KitsuneIllusion extends ManasSubConfig {
        @Comment("Magicule Acquirement Cost.")
        public double mpAcquirement = 100.0;
        @Comment("Skill Mastery Points.")
        public double masteryPoints = 1_000;
        @Comment("Damage cap for Sharpened Claws")
        public double sharpClaws = 100;
        @Comment("Percent MP drain from Fox Drain.")
        public double foxMP = 0.01;
        @Comment("Percent AP drain from Fox Drain.")
        public double foxAP = 0.01;
        @Comment("Final Evo MP/AP drain chance.")
        public double percentDrain = 0.2;

        public KitsuneIllusion() {
        }
    }


}
