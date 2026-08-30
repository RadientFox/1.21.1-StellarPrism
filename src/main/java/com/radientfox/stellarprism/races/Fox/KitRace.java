package com.radientfox.stellarprism.races.Fox;

import com.radientfox.stellarprism.Registry.main.StellarRaces;
import com.radientfox.stellarprism.Registry.main.skills.StellarIntrinsics;
import com.radientfox.stellarprism.config.races.fox.FoxRaceConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.BeastfolkConfig;
import io.github.manasmods.tensura.config.race.RaceConfig;
import io.github.manasmods.tensura.config.race.SlimeConfig;
import io.github.manasmods.tensura.race.template.DefaultRace;
import io.github.manasmods.tensura.registry.race.TensuraRaces;
import io.github.manasmods.tensura.registry.skill.CommonSkills;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class KitRace extends DefaultRace {
        public KitRace(ManasRace.Difficulty difficulty) {
            super(difficulty);
        }

        public KitRace() {
            this(Difficulty.EXTREME);
            this.applyDefaultAttributeModifiers();
            FoxRaceConfig.Kit config = ((FoxRaceConfig)ConfigRegistry.getConfig(FoxRaceConfig.class)).Kit;
            this.addAttributeModifier(Attributes.JUMP_STRENGTH, DEFAULT_RACE_ID, config.jumpStrength, AttributeModifier.Operation.ADD_VALUE);
            this.addAttributeModifier(Attributes.SAFE_FALL_DISTANCE, DEFAULT_RACE_ID, 2.0 * config.jumpStrength, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        }

        public RaceConfig.Default getDefaultConfig() {
            return ((FoxRaceConfig) ConfigRegistry.getConfig(FoxRaceConfig.class)).Kit;
        }

        public @Nullable ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
            return (ManasRace) StellarRaces.ELEMENTAL_KIT.get();
        }

        public @Nullable ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
            return (ManasRace)StellarRaces.ELEMENTAL_KIT.get();
        }

        public @Nullable ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
            return (ManasRace)StellarRaces.ELEMENTAL_KIT.get();
        }

        public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
            return List.of((ManasRace)StellarRaces.ELEMENTAL_KIT.get());
        }

        public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
            List<ManasSkill> list = new ArrayList();
            list.add((ManasSkill) IntrinsicSkills.BEAST_TRANSFORMATION.get());
            list.add((ManasSkill) CommonSkills.SELF_REGENERATION.get());
            list.add((ManasSkill) StellarIntrinsics.KitsuneIllusion.get());
            return list;
        }
}
