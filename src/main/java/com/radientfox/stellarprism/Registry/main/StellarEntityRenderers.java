package com.radientfox.stellarprism.Registry.main;

import com.radientfox.stellarprism.Registry.main.StellarEntityTypes;
import com.radientfox.stellarprism.client.renderer.SpiralElectroBlastBeamRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class StellarEntityRenderers {

    public static void register(FMLClientSetupEvent event) {

        EntityRenderers.register(
                StellarEntityTypes.SPIRAL_ELECTRO_BLAST.get(),
                SpiralElectroBlastBeamRenderer::new
        );

    }
}