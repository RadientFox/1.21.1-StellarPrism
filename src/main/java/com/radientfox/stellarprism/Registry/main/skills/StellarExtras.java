package com.radientfox.stellarprism.Registry.main.skills;

import com.radientfox.stellarprism.ability.extra.VoidSubordinate;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.impl.SkillRegistry;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class StellarExtras {

    public static final RegistrySupplier<VoidSubordinate> VOID_SUBORDINATE_SKILL = register("void_subordinate_skill", (Supplier<VoidSubordinate>) VoidSubordinate::new);


    private static <E extends ManasSkill> RegistrySupplier<E> register(String name, Supplier<E> supplier) {
        return SkillRegistry.SKILLS.register(ResourceLocation.fromNamespaceAndPath("stellarprism", name), supplier);
    }

    //   =====================
    //   | Unique Skills |
    //   =====================
    public StellarExtras() {
    }


    public static void init() {
    }


}
