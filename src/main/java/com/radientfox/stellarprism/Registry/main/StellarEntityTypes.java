package com.radientfox.stellarprism.Registry.main;

import com.radientfox.stellarprism.StellarPrism;
import com.radientfox.stellarprism.ability.entity.beam.SpiralElectroBlastBeam;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class StellarEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, StellarPrism.MODID);


    public static final DeferredHolder<EntityType<?>, EntityType<SpiralElectroBlastBeam>> SPIRAL_ELECTRO_BLAST =
            ENTITY_TYPES.register("spiral_electro_blast",
                    () -> EntityType.Builder.<SpiralElectroBlastBeam>of(
                                    SpiralElectroBlastBeam::new,
                                    MobCategory.MISC
                            )
                            .sized(0.1F, 0.1F)
                            .clientTrackingRange(64)
                            .updateInterval(1)
                            .build("spiral_electro_blast"));
}