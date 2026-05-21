package com.radientfox.stellarprism;

import com.radientfox.stellarprism.Regestrys.StellarRegistry;
import com.radientfox.stellarprism.config.StellarConfigs;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.UUID;

@Mod(StellarPrism.MODID)
public class StellarPrism {
    public static final String MODID = "stellarprism";
    public static final Logger LOGGER = LogUtils.getLogger();

    public StellarPrism(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        StellarRegistry.register(modEventBus);
        StellarConfigs.init();

        NeoForge.EVENT_BUS.register(this);
    }

    public static final UUID RADIENTFOX = UUID.fromString("0b336360-d31b-49a1-b259-c705465a0bbb");
    public static final UUID TERRACHARM = UUID.fromString("3c930a59-4d3d-4e4f-b62b-2f71073e1bbb");
    public static final UUID GOOBER = UUID.fromString("28d1afb7-d25b-4c89-9ffc-ac468e39ab07");


    //patreon
    public static final UUID NITE = UUID.fromString("2738f60c-9101-4939-b3d3-49e08b2ec525");



    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        StellarConfigs.init();


    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
}
