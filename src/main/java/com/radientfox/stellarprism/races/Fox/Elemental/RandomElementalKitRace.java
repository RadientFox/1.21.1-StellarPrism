package com.radientfox.stellarprism.races.Fox.Elemental;

import com.radientfox.stellarprism.Registry.main.StellarRaces;
import com.radientfox.stellarprism.config.races.fox.FoxRaceConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig;
import io.github.manasmods.tensura.race.template.DefaultRace;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.registry.item.TensuraMobDropItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RandomElementalKitRace extends DefaultRace {
    protected static final FoxRaceConfig.ElementalKit CONFIG;

    public RandomElementalKitRace(Difficulty difficulty) {
            super(difficulty);
        }

        public RandomElementalKitRace() {
            this(Difficulty.EASY);
            this.applyDefaultAttributeModifiers();
        }

    public RaceConfig.Default getDefaultConfig() {
        return CONFIG;
    }


    public @Nullable ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
            return (ManasRace) StellarRaces.ELEMENTAL_FOX.get();
        }

        public @Nullable ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
            return (ManasRace)StellarRaces.ELEMENTAL_FOX.get();
        }

        public @Nullable ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
            return (ManasRace)StellarRaces.ELEMENTAL_FOX.get();
        }

        public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
            return List.of((ManasRace) StellarRaces.ELEMENTAL_FOX.get());
        }

        public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
            List<ManasSkill> list = new ArrayList();


            return list;
        }


    public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {
        return Map.of(new EvolutionRequirement.EPRequirement((CONFIG.epRequirement)), 50.0F, new EvolutionRequirement.ItemConsumeRequirement((Item) TensuraMobDropItems.ELEMENTAL_ESSENCE.get(), (int) CONFIG.elementalEssence), 50.0F);
    }

    static {
        CONFIG = ((FoxRaceConfig)ConfigRegistry.getConfig(FoxRaceConfig.class)).ElementalKit;
    }
}
