package com.radientfox.stellarprism.Regestrys.main;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.awt.*;

public class StellarEffects {

    private static final DeferredRegister<MobEffect> registry;




   // public static final RegistryObject<MobEffect> RED_SPRAY;


    public static void setRegistry(IEventBus modEventBus) {
        registry.register(modEventBus);
    }

    static {
        registry = DeferredRegister.create(Registries.MOB_EFFECT, "stellarprism");
    /*
        RED_SPRAY = registry.register("red_spray", () -> {
            return new RedSpray(MobEffectCategory.BENEFICIAL, (new Color(190, 68, 246)).getRGB());
        });


     */
    }
}

