package com.radientfox.stellarprism.Registry.main.skills;

import com.radientfox.stellarprism.ability.Unique.JadeSkill;
import com.radientfox.stellarprism.ability.Unique.SpinelSkill;
import com.radientfox.stellarprism.ability.Unique.VoidPriestess;
import com.radientfox.stellarprism.ability.Unique.NimueSkill;
import com.radientfox.stellarprism.ability.Unique.DullahanSkill;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.impl.SkillRegistry;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class StellarUniques {

    public static final RegistrySupplier<SpinelSkill> SPINEL_SKILL = register("spinel_skill", (Supplier<SpinelSkill>) SpinelSkill::new);
    public static final RegistrySupplier<JadeSkill> JADE_SKILL = register("jade_skill", (Supplier<JadeSkill>) JadeSkill::new);
    public static final RegistrySupplier<VoidPriestess> VOID_PRIESTESS_SKILL = register("void_priestess_skill", (Supplier<VoidPriestess>) VoidPriestess::new);
    public static final RegistrySupplier<ManasSkill> NIMUE = register("nimue", NimueSkill::new);
    public static final RegistrySupplier<ManasSkill> DULLAHAN = register("dullahan", DullahanSkill::new);



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
