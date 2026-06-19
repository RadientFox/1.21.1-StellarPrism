package com.radientfox.stellarprism.Registry;

import com.radientfox.stellarprism.Registry.main.StellarEffects;
import com.radientfox.stellarprism.Registry.main.StellarEnchantments;
import com.radientfox.stellarprism.Registry.main.StellarItems;
import com.radientfox.stellarprism.Registry.main.StellarRaces;
import com.radientfox.stellarprism.Registry.main.skills.*;

public class StellarRegistry {


    public static void register(net.neoforged.bus.api.IEventBus modEventBus) {

        StellarIntrinsics.init();
        StellarExtras.init();
        StellarUniques.init();
        StellarUltimates.register(modEventBus);
        StellarEffects.register(modEventBus);
        StellarItems.register(modEventBus);
        StellarRaces.init();
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
