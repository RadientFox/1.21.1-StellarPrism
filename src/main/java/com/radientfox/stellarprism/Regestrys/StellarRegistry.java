package com.radientfox.stellarprism.Regestrys;

import com.radientfox.stellarprism.Regestrys.main.StellarEffects;
import com.radientfox.stellarprism.Regestrys.main.StellarEnchantments;
import com.radientfox.stellarprism.Regestrys.main.StellarItems;
import com.radientfox.stellarprism.Regestrys.main.skills.*;

public class StellarRegistry {


    public static void register(net.neoforged.bus.api.IEventBus modEventBus) {

        StellarIntrinsics.register(modEventBus);
        StellarExtras.register(modEventBus);
        StellarUniques.init();
        StellarUltimates.register(modEventBus);
        StellarEffects.setRegistry(modEventBus);
        StellarItems.register(modEventBus);
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
