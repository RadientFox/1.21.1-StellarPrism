package com.radientfox.stellarprism.Registry;

import com.radientfox.stellarprism.Registry.main.*;
import com.radientfox.stellarprism.Registry.main.skills.*;
import com.radientfox.stellarprism.Registry.main.StellarEntityRenderers;
import com.radientfox.stellarprism.storages.KitsuneElementStorage;

public class StellarRegistry {


    public static void register(net.neoforged.bus.api.IEventBus modEventBus) {

        StellarDimensions.DIMENSION_TYPES.register(modEventBus);
        StellarIntrinsics.init();
        StellarExtras.init();
        StellarUniques.init();
        StellarUltimates.register(modEventBus);
        StellarEffects.register(modEventBus);
        StellarItems.register(modEventBus);
        StellarRaces.init();
        StellarEntityTypes.ENTITY_TYPES.register(modEventBus);
        modEventBus.addListener(StellarEntityRenderers::register);
        KitsuneElementStorage.init();
        //  StellarToolsandWepons.register(modEventBus);
        //   StellarEntities.register(modEventBus);
        StellarEnchantments.init(modEventBus);
        //   StellarSounds.register(modEventBus);
        //  StellarBlocks.init(modEventBus);

        //StellarFeatures.init(modEventBus);
        //StellarPlacedFeatures.init(modEventBus);
        // StellarParticles.register(modEventBus);
        StellarResistances.register(modEventBus);




    }

}