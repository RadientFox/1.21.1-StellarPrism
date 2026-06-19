package com.radientfox.stellarprism.Registry.main.skills;

import com.radientfox.stellarprism.StellarPrism;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class StellarResistances {


    public static DeferredRegister<ManasSkill> skillRegistry = DeferredRegister.create(SkillAPI.getSkillRegistryKey(), StellarPrism.MODID);

    public static void register(IEventBus modEventBus) {
        skillRegistry.register(modEventBus);
    }


    //   =====================
    //   | Resistance Skills |
    //   =====================

    //public static final RegistryObject<CrimsonArmor> CrimsonArmor =
      //      skillRegistry.register("crimson_armor", CrimsonArmor::new);


}
