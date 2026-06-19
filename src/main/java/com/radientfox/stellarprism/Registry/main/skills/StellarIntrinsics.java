package com.radientfox.stellarprism.Registry.main.skills;

import com.radientfox.stellarprism.StellarPrism;
import com.radientfox.stellarprism.ability.Intrinsics.VulpusSkill;
import com.radientfox.stellarprism.ability.extra.VoidSubordinate;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillRegistry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class StellarIntrinsics {


    public static final RegistrySupplier<VulpusSkill> Kitsune_Illusion = register("kitsune_illusion", (Supplier<VulpusSkill>) VulpusSkill::new);


    private static <E extends ManasSkill> RegistrySupplier<E> register(String name, Supplier<E> supplier) {
        return SkillRegistry.SKILLS.register(ResourceLocation.fromNamespaceAndPath("stellarprism", name), supplier);
    }

    //   =====================
    //   | Intrinsic Skills |
    //   =====================
    public StellarIntrinsics() {
    }


    public static void init() {
    }

}
