//package com.radientfox.stellarprism.Registry.main.skills;
//
//import com.radientfox.stellarprism.StellarPrism;
//import com.radientfox.stellarprism.ability.Ultimate.LadyInWhiteSkill;
//import com.radientfox.stellarprism.ability.Unique.NimueSkill;
//import dev.architectury.registry.registries.RegistrySupplier;
//import io.github.manasmods.manascore.skill.api.ManasSkill;
//import io.github.manasmods.manascore.skill.api.SkillAPI;
//import io.github.manasmods.manascore.skill.impl.SkillRegistry;
//import net.minecraft.resources.ResourceLocation;
//import net.neoforged.bus.api.IEventBus;
//import net.neoforged.neoforge.registries.DeferredRegister;
//
//import java.util.function.Supplier;
//
//public class StellarUltimates {
//
//
//    public static DeferredRegister<ManasSkill> skillRegistry = DeferredRegister.create(SkillAPI.getSkillRegistryKey(), StellarPrism.MODID);
//
//    public static void register(IEventBus modEventBus) {
//        skillRegistry.register(modEventBus);
//    }
//
//
//    //   =====================
//    //   | Ultimate Skills |
//    //   =====================
//
//    //public static final RegistryObject<CrimsonArmor> CrimsonArmor =
//      //      skillRegistry.register("crimson_armor", CrimsonArmor::new);
//
//    public static final RegistrySupplier<ManasSkill> LADY_IN_WHITE = register("lady_in_white", LadyInWhiteSkill::new);
//
//private static <E extends ManasSkill> RegistrySupplier<E> register(String name, Supplier<E> supplier) {
//    return SkillRegistry.SKILLS.register(ResourceLocation.fromNamespaceAndPath("stellarprism", name), supplier);
//}
//
//
//public StellarUltimates() {
//}
//
//
//public static void init() {
//}
//
//}
