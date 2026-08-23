package com.radientfox.stellarprism.item;

import com.radientfox.stellarprism.Registry.main.StellarItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/*
public class StellarItemTab {


    private static final DeferredRegister<CreativeModeTab> TABS;
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> STELLAR_BASIC_TAB;

    private StellarItemTab() {
    }

    public static void register(IEventBus modEventBus) {
        TABS.register(modEventBus);
    }


    private static void displayTabItems(CreativeModeTab.ItemDisplayParameters params, CreativeModeTab.Output out) {
        out.accept((ItemLike) StellarItems.ELEMENT_CORE_TIME);
    }

    static {
        TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "stellarprism");
        STELLAR_BASIC_TAB = TABS.register("stellar-tab", () -> {
            return CreativeModeTab.builder().title(Component.translatable("itemGroup.stellar_items")).icon(() -> {
                return new ItemStack((ItemLike)StellarItems.ELEMENT_CORE_TIME);
            }).displayItems(StellarItemTab::displayTabItems).build();
        });
    }
}


 */