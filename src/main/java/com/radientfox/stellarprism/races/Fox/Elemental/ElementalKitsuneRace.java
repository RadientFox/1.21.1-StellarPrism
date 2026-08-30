package com.radientfox.stellarprism.races.Fox.Elemental;

import com.radientfox.stellarprism.Registry.main.StellarItems;
import com.radientfox.stellarprism.config.races.fox.FoxRaceConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.config.race.RaceConfig;
import io.github.manasmods.tensura.race.template.DefaultRace;
import io.github.manasmods.tensura.race.template.EvolutionRequirement;
import io.github.manasmods.tensura.registry.item.TensuraMaterialItems;
import io.github.manasmods.tensura.registry.item.TensuraMobDropItems;
import io.github.manasmods.tensura.registry.race.TensuraRaces;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ElementalKitsuneRace extends DefaultRace {
    protected static final FoxRaceConfig.ElementalKitsune CONFIG;

    public ElementalKitsuneRace(Difficulty difficulty) {
        super(difficulty);
    }

    public ElementalKitsuneRace() {
        this(Difficulty.EASY);
        this.applyDefaultAttributeModifiers();
    }

    public RaceConfig.Default getDefaultConfig() {
        return CONFIG;
    }


    public @Nullable ManasRace getDefaultEvolution(ManasRaceInstance instance, LivingEntity entity) {
        return (ManasRace) TensuraRaces.BEAST_LORD.get();
    }

    public @Nullable ManasRace getAwakeningEvolution(ManasRaceInstance instance, LivingEntity entity) {
        return (ManasRace)TensuraRaces.SPIRIT_BEAST.get();
    }

    public @Nullable ManasRace getHarvestFestivalEvolution(ManasRaceInstance instance, LivingEntity entity) {
        return (ManasRace)TensuraRaces.BEAST_LORD.get();
    }

    public List<ManasRace> getNextEvolutions(ManasRaceInstance instance, LivingEntity entity) {
        return List.of((ManasRace)TensuraRaces.BEAST_LORD.get());
    }

    public List<ManasSkill> getIntrinsicSkills(ManasRaceInstance instance, LivingEntity entity) {
        List<ManasSkill> list = new ArrayList();
        if ( KitsuneElement.getElement(entity) == KitsuneElement.FLAME){
            list.add(ExtraSkills.FLAME_MANIPULATION.get());
            list.add(ResistanceSkills.FLAME_ATTACK_RESISTANCE.get());
        }
        if ( KitsuneElement.getElement(entity) == KitsuneElement.EARTH){
            list.add(ExtraSkills.EARTH_MANIPULATION.get());
            list.add(ResistanceSkills.EARTH_ATTACK_RESISTANCE.get());
        }
        if ( KitsuneElement.getElement(entity) == KitsuneElement.WATER){
            list.add(ExtraSkills.WATER_MANIPULATION.get());
            list.add(ResistanceSkills.WATER_ATTACK_RESISTANCE.get());
        }
        if ( KitsuneElement.getElement(entity) == KitsuneElement.WIND){
            list.add(ExtraSkills.WIND_MANIPULATION.get());
            list.add(ResistanceSkills.WIND_ATTACK_RESISTANCE.get());
        }
        if ( KitsuneElement.getElement(entity) == KitsuneElement.SPACE){
            list.add(ExtraSkills.SPATIAL_MANIPULATION.get());
            list.add(ResistanceSkills.SPATIAL_ATTACK_RESISTANCE.get());
        }
        if ( KitsuneElement.getElement(entity) == KitsuneElement.TIME){
            list.add(ResistanceSkills.SPIRITUAL_ATTACK_RESISTANCE.get());
            list.add(ResistanceSkills.ABNORMAL_CONDITION_RESISTANCE.get());
        }
        if ( KitsuneElement.getElement(entity) == KitsuneElement.GRAVITY){
            list.add(ResistanceSkills.GRAVITY_ATTACK_RESISTANCE.get());
            list.add(ExtraSkills.GRAVITY_MANIPULATION.get());
        }



        return list;
    }


    public Map<EvolutionRequirement, Float> getEvolutionRequirements(ManasRaceInstance previous, LivingEntity entity) {


        EvolutionRequirement elementReq = new EvolutionRequirement.ItemCarryingRequirement((Item) TensuraMobDropItems.ELEMENTAL_ESSENCE.get(), 100);
        EvolutionRequirement elementReq2 = new EvolutionRequirement.ItemCarryingRequirement((Item) TensuraMobDropItems.ELEMENTAL_ESSENCE.get(), 1);



        switch (KitsuneElement.getElement(entity)) {
            case  FLAME -> elementReq = new EvolutionRequirement.ItemCarryingRequirement((Item) TensuraMaterialItems.ELEMENT_CORE_FIRE.get(), 1);
            case  EARTH -> elementReq = new EvolutionRequirement.ItemCarryingRequirement((Item) TensuraMaterialItems.ELEMENT_CORE_EARTH.get(), 1);
            case  WATER -> elementReq = new EvolutionRequirement.ItemCarryingRequirement((Item) TensuraMaterialItems.ELEMENT_CORE_WATER.get(), 1);
            case  WIND -> elementReq = new EvolutionRequirement.ItemCarryingRequirement((Item) TensuraMaterialItems.ELEMENT_CORE_WIND.get(), 1);
            case  TIME -> elementReq = new EvolutionRequirement.ItemCarryingRequirement((Item) StellarItems.ELEMENT_CORE_TIME.get(), 1);
            case  SPACE -> elementReq = new EvolutionRequirement.ItemCarryingRequirement((Item) TensuraMaterialItems.ELEMENT_CORE_SPACE.get(), 1);
            case SPACETIME -> {
                elementReq = (new EvolutionRequirement.ItemCarryingRequirement((Item) TensuraMaterialItems.ELEMENT_CORE_SPACE.get(), 1));
                elementReq2 = (new EvolutionRequirement.ItemCarryingRequirement((Item) StellarItems.ELEMENT_CORE_TIME.get(), 1));
            }
            case GRAVITY -> {
                elementReq = (new EvolutionRequirement.ItemCarryingRequirement((Item) TensuraMaterialItems.ELEMENT_CORE_EARTH.get(), 5));
            }
        }

        return Map.of(new EvolutionRequirement.EPRequirement((CONFIG.epRequirement)), 25F, elementReq, 25F, elementReq2, 25F, new EvolutionRequirement.ItemConsumeRequirement((Item) TensuraMobDropItems.ELEMENTAL_ESSENCE.get(), (int) CONFIG.elementalEssence), 25F);
    }


    static {
        CONFIG = ((FoxRaceConfig) ConfigRegistry.getConfig(FoxRaceConfig.class)).ElementalKitsune;
    }
}
