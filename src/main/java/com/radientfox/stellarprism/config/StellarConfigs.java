package com.radientfox.stellarprism.config;

import com.radientfox.stellarprism.config.races.fox.FoxRaceConfig;
import com.radientfox.stellarprism.config.skills.StellarUniqueConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;

public class StellarConfigs {
    public StellarConfigs() {
    }

    public static void init() {

        ConfigRegistry.registerConfig(new StellarUniqueConfig());
        ConfigRegistry.registerConfig(new FoxRaceConfig());

    }
}
