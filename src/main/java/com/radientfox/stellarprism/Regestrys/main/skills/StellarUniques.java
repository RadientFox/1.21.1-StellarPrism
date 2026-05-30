package com.radientfox.stellarprism.Regestrys.main.skills;

import com.radientfox.stellarprism.ability.Unique.JadeSkill;
import com.radientfox.stellarprism.ability.Unique.SpinelSkill;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillRegistry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class StellarUniques {

    public static final RegistrySupplier<SpinelSkill> SPINEL_SKILL = register("spinel_skill", (Supplier<SpinelSkill>) SpinelSkill::new);
    public static final RegistrySupplier<JadeSkill> JADE_SKILL = register("jade_skill", (Supplier<JadeSkill>) JadeSkill::new);


    private static <E extends ManasSkill> RegistrySupplier<E> register(String name, Supplier<E> supplier) {
        return SkillRegistry.SKILLS.register(ResourceLocation.fromNamespaceAndPath("stellarprism", name), supplier);
    }

    //   =====================
    //   | Unique Skills |
    //   =====================
    public StellarUniques() {
    }


    public static void init() {
    }

}
