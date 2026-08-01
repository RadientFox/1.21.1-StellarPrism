package com.radientfox.stellarprism.Registry;

import com.radientfox.stellarprism.StellarPrism;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class StellarDimensions {

    public static final String SUBSPACE_PREFIX = "subspace_";
    public static final DeferredRegister<DimensionType> DIMENSION_TYPES = DeferredRegister.create(Registries.DIMENSION_TYPE, StellarPrism.MODID);
    public static final ResourceKey<DimensionType> SUBSPACE_TYPE;

    private StellarDimensions() {
    }

    static {
        SUBSPACE_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, ResourceLocation.fromNamespaceAndPath("stellarprism", "subspace"));
    }
}