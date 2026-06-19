package com.radientfox.stellarprism.Registry.main;

import com.radientfox.stellarprism.races.Fox.Elemental.ElementalKitRace;
import com.radientfox.stellarprism.races.Fox.KitRace;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.impl.RaceRegistry;
import io.github.manasmods.tensura.race.demon.DevilLordRace;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class StellarRaces {

    public static final RegistrySupplier<ManasRace> KIT = register("kit", KitRace::new);
    public static final RegistrySupplier<ManasRace> ELEMENTAL_KIT = register("elemental_kit", ElementalKitRace::new);

    public StellarRaces() {
    }

    private static <E extends ManasRace> RegistrySupplier<E> register(String name, Supplier<E> supplier) {
        return RaceRegistry.RACES.register(ResourceLocation.fromNamespaceAndPath("stellarprism", name), supplier);
    }

    public static void init() {
    }
}
