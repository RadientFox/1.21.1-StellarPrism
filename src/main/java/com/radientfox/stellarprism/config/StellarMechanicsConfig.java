package com.radientfox.stellarprism.config;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.tensura.config.ReincarnationConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;

import java.util.List;

public class StellarMechanicsConfig extends ManasConfig {
    public ReincarnationConfig.Races HEARTSTONES = new ReincarnationConfig.Races();

    public StellarMechanicsConfig() {
    }


    public String getFileName() {
        return "stellarprism/mechanics_config";
    }

    public static class Heartstones extends ManasSubConfig {
        @Comment("List of Heartstone Skills .")
        public List<String> listofHeartstones = List.of("stellarprism:spinel_skill", "stellarprism:jade_skill");

        public Heartstones() {
        }
    }

}
