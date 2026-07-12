package com.radientfox.stellarprism.Registry.main;

import com.github.hvnbael.trnightmare.main.entity.sentientboss.TrSentientBossGiiCrimsonEntity;
import com.radientfox.stellarprism.entities.bosses.HerobrianeSentientBoss;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class StellarEntities {

    private static final DeferredRegister<EntityType<?>> ENTITIES;
    public static final DeferredHolder<EntityType<?>, EntityType<HerobrianeSentientBoss>> SENTIENT_HEROBRINE_BOSS;

    private static String key(String path) {
        return ResourceLocation.fromNamespaceAndPath("stellarprism", path).toString();
    }

    private static <E extends Entity> EntityType<E> humanoid(EntityType.EntityFactory<E> factory, String path) {
        return EntityType.Builder.of(factory, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(10).build(key(path));
    }

    private StellarEntities() {
    }

    public static void register(IEventBus modBus) {
        ENTITIES.register(modBus);
    }

    static {
        ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, "stellarprism");
        SENTIENT_HEROBRINE_BOSS = ENTITIES.register("sentient_herobine_boss", () -> {
            return humanoid(HerobrianeSentientBoss::new, "sentient_herobine_boss");
        });
    }

    }
