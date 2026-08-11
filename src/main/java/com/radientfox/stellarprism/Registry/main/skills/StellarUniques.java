package com.radientfox.stellarprism.Registry.main.skills;

import com.radientfox.stellarprism.ability.Unique.*;
import dev.architectury.registry.registries.RegistrySupplier;
import com.radientfox.stellarprism.ability.Unique.Digimon.Agumon.AgumonSkill;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.impl.SkillRegistry;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class StellarUniques {

    public static final RegistrySupplier<SpinelSkill> SPINEL_SKILL = register("spinel_skill", (Supplier<SpinelSkill>) SpinelSkill::new);
    public static final RegistrySupplier<JadeSkill> JADE_SKILL = register("jade_skill", (Supplier<JadeSkill>) JadeSkill::new);
    public static final RegistrySupplier<TestingSkill> TEST_SKILL = register("test_skill", (Supplier<TestingSkill>) TestingSkill::new);
    public static final RegistrySupplier<VoidPriestess> VOID_PRIESTESS_SKILL = register("void_priestess_skill", (Supplier<VoidPriestess>) VoidPriestess::new);
    public static final RegistrySupplier<ManasSkill> NIMUE = register("nimue", NimueSkill::new);
    public static final RegistrySupplier<ManasSkill> DULLAHAN = register("dullahan", DullahanSkill::new);
    public static final RegistrySupplier<ManasSkill> PENDRAGON = register("pendragon", PendragonSkill::new);
    public static final RegistrySupplier<ManasSkill> CHOSEN_KING = register("chosen_king", ChosenKingSkill::new);
    public static final RegistrySupplier<ManasSkill> SPIRAL_HEART = register("spiral_heart", SpiralHeartSkill::new);
    public static final RegistrySupplier<ManasSkill> INTERLOPER = register("interloper", InterloperSkill::new);
    public static final RegistrySupplier<ManasSkill> TENACIOUS = register("tenacious", TenaciousSkill::new);
    public static final RegistrySupplier<ManasSkill> FAKER = register("faker", FakerSkill::new);
    public static final RegistrySupplier<ManasSkill> AGUMON_SKILL = register("agumon_skill", AgumonSkill::new);





    private static <E extends ManasSkill> RegistrySupplier<E> register(String name, Supplier<E> supplier) {
        return SkillRegistry.SKILLS.register(ResourceLocation.fromNamespaceAndPath("stellarprism", name), supplier);
    }


    public StellarUniques() {
    }


    public static void init() {
    }

}
