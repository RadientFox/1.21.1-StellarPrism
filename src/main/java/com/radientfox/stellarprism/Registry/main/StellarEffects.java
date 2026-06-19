package com.radientfox.stellarprism.Registry.main;

import com.radientfox.stellarprism.effects.SpatialAuraEffect;
import com.radientfox.stellarprism.effects.VoidSubordinateEffect;
import com.radientfox.stellarprism.effects.WindAuraEffect;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StellarEffects {


    public static final DeferredRegister<MobEffect> MOB_EFFECTS;
    private static final Map<RegistrySupplier<MobEffect>, Holder<MobEffect>> HOLDER_CACHE;
    public static final DeferredHolder<MobEffect, MobEffect> SPACEAURA;
    public static final DeferredHolder<MobEffect, MobEffect> WINDAURA;
    public static final DeferredHolder<MobEffect, MobEffect> VOID_SUBORDINATE;



   // public static final RegistryObject<MobEffect> RED_SPRAY;

    public StellarEffects() {
    }

    public static void register(IEventBus bus) {
        MOB_EFFECTS.register(bus);
    }


    static {
        MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, "stellarprism");
        HOLDER_CACHE = new ConcurrentHashMap();
        SPACEAURA = MOB_EFFECTS.register("space_aura", SpatialAuraEffect::new);
        WINDAURA = MOB_EFFECTS.register("wind_aura", WindAuraEffect::new);
        VOID_SUBORDINATE = MOB_EFFECTS.register("void_subordinate", VoidSubordinateEffect::new);
    }
}

