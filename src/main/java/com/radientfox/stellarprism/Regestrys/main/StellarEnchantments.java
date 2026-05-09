package com.radientfox.stellarprism.Regestrys.main;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class StellarEnchantments {


    public static final DeferredHolder<Enchantment, Enchantment> FLAME_ATTUNMENT = holder("flame_attunment");
    public static final DeferredHolder<Enchantment, Enchantment> EARTH_ATTUNMENT = holder("earth_attunment");

    public StellarEnchantments() {
    }

    public static void init(IEventBus modEventBus) {
    }

    private static DeferredHolder<Enchantment, Enchantment> holder(String id) {
        return DeferredHolder.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath("stellarprism", id));
    }
}
