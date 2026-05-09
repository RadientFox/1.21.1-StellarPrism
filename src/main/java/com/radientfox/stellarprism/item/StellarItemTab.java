package com.radientfox.stellarprism.item;

import com.radientfox.stellarprism.Regestrys.main.StellarItems;
import io.github.manasmods.tensura.registry.item.TensuraToolItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class StellarItemTab {


    private static final DeferredRegister<CreativeModeTab> TABS;
   // public static final DeferredHolder<CreativeModeTab, CreativeModeTab> STELLAR_BASIC_TAB;

    private StellarItemTab() {
    }

    public static void register(IEventBus modEventBus) {
        TABS.register(modEventBus);
    }


    private static void displayTabItems(CreativeModeTab.ItemDisplayParameters params, CreativeModeTab.Output out) {
       // out.accept((ItemLike) StellarItems.PRISM_SHARD);
    }

    static {
        TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "trnightmare");
       // STELLAR_BASIC_TAB = TABS.register("03-gears", () -> {
       //     return CreativeModeTab.builder().title(Component.translatable("itemGroup.nightmare_weapons")).icon(() -> {
         //       return new ItemStack((ItemLike)StellarItems.PRISM_SHARD);
       //     }).displayItems(StellarItemTab::displayTabItems).build();
       // });
    }
}
