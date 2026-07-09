package com.radientfox.stellarprism.Registry.main;

import com.github.hvnbael.trnightmare.main.weapon.Excalibur;
import com.radientfox.stellarprism.StellarPrism;
import io.github.manasmods.tensura.item.TensuraToolTiers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class StellarItems {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, StellarPrism.MODID);
   // public static final DeferredItem<SimpleFoodItem> PRISM_SHARD;
 /*   public static final RegistryObject<Item> CORRUPTION_SHARD;
    public static final RegistryObject<Item> VILLAINOUS_SPIRIT;
    public static final RegistryObject<Item> HEROES_SPIRIT;
    public static final RegistryObject<Item> HERO_SHARD;
    public static final RegistryObject<Item> RANDOM_AMULET;
    public static final RegistryObject<Item> RANDOM_HEARTSTONE;;
    public static final RegistryObject<Item> CHOCO;
    public static final RegistryObject<Item> MONKEY_SPIN;
    public static final RegistryObject<Item> WORLDS_MEMORY;
    public static final RegistryObject<Item> REALITY_MEMORY;
    public static final RegistryObject<Item> POWER_MEMORY;
    public static final RegistryObject<Item> HOPE_MEMORY;
    public static final RegistryObject<Item> TIME_MEMORY;
    public static final RegistryObject<Item> SPACE_MEMORY;
    public static final RegistryObject<Item> MOLECULARESSENCE;
    public static final RegistryObject<Item> LUNARESSENCE;
    public static final RegistryObject<Item> PLANETARYESSENCE;
    public static final RegistryObject<Item> SOLARESSENCE;
    public static final RegistryObject<Item> GALACTICESSENCE;
    public static final RegistryObject<Item> UNIVERSALESSENCE;
    public static final RegistryObject<Item> CDRIVE_ITEM;
    public static final RegistryObject<Item> MONSTER_POTION;
    public static final RegistryObject<Item> ROBOT_POTION;
    public static final RegistryObject<Item> LUCKY_ONE_POTION;
    public static final RegistryObject<Item> CALAMITY_PRIEST_POTION;
    public static final RegistryObject<Item> WINNER_POTION;
    public static final RegistryObject<Item> MISFORTUNE_MAGE_POTION;
    public static final RegistryObject<Item> CHAOSWALKER_POTION;
    public static final RegistryObject<Item> SOOTHSAYER_POTION;
    public static final RegistryObject<Item> SNAKE_OF_MERCURY_POTION;
    public static final RegistryObject<Item> WOF_POTION;


    public static final RegistryObject<Item> RUBY_SHARD;
    public static final RegistryObject<Item> TOPAZ_SHARD;
    public static final RegistryObject<Item> CITRINE_SHARD;
    public static final RegistryObject<Item> JADE_SHARD;
    public static final RegistryObject<Item> AQUA_SHARD;
    public static final RegistryObject<Item> SUGLITE_SHARD;
    public static final RegistryObject<Item> SPINEL_SHARD;
    public static final RegistryObject<Item> ONYX_SHARD;
    public static final RegistryObject<Item> OPAL_SHARD;
    public static final RegistryObject<Item> ELDRITCH_CORRUPTION;


  */
   public static final DeferredHolder<Item, Excalibur> EXCALIBUR;
    public StellarItems() {
    }


    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }


    static {
/*
        PRISM_SHARD = (DeferredItem<SimpleFoodItem>) ITEMS.register("prism_shard", () -> {
            return new SimpleFoodItem(StellarFoodProperties.PRISM_SHARD);
        });

        CORRUPTION_SHARD = ITEMS.register("corruption_shard", () -> {
            return new SimpleFoodItem(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0.0F).effect(new MobEffectInstance(()    TensuraMobEffects.CURSE, 100, 0), 1.0F).effect(new MobEffectInstance(MobEffects.WITHER , 100, 0), 1.0F).build());
        });
        VILLAINOUS_SPIRIT = ITEMS.register("villainous_spirit", () -> {
            return new SimpleFoodItem(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0.0F).effect(new MobEffectInstance((MobEffect)TensuraMobEffects.CURSE.get(), 100, 0), 1.0F).effect(new MobEffectInstance(MobEffects.WITHER , 100, 0), 1.0F).build());
        });
        HEROES_SPIRIT = ITEMS.register("heroes_spirit", () -> {
            return new SimpleFoodItem(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0.0F).effect(new MobEffectInstance(MobEffects.LUCK, 100, 0), 1.0F).effect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE , 100, 0), 1.0F).build());
        });
        HERO_SHARD = ITEMS.register("hero_shard", () -> {
            return new SimpleFoodItem(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0.0F).effect(new MobEffectInstance(MobEffects.LUCK, 100, 0), 1.0F).effect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE , 100, 0), 1.0F).build());
        });
       // RANDOM_AMULET = ITEMS.register("random_amulet", AmuletBook::new);
      //  RANDOM_HEARTSTONE = ITEMS.register("random_heartstone", HeartstoneBook::new);

        CDRIVE_ITEM = ITEMS.register("cdrive_item", () -> {
            return new SimpleFoodItem(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0.0F).effect(new MobEffectInstance((MobEffect)TensuraMobEffects.CURSE.get(), 100, 0), 1.0F).effect(new MobEffectInstance(MobEffects.WITHER , 100, 0), 1.0F).build());
        });

        CHOCO = ITEMS.register("chocolate", () -> {
            return new ChocoItem(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(1.0F).effect(new MobEffectInstance(MobEffects.DIG_SPEED, 100, 0), 1.0F).effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED , 100, 0), 1.0F).build());
        });

        MONSTER_POTION = ITEMS.register("monster_potion", () -> {
            return new SimpleFoodItem(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(1.0F).build());
        });

        ROBOT_POTION = ITEMS.register("robot_potion", () -> {
            return new SimpleFoodItem(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(1.0F).build());
        });

         LUCKY_ONE_POTION = ITEMS.register("lucky_one_potion", () -> {
            return new SimpleFoodItem(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(1.0F).build());
        });

         CALAMITY_PRIEST_POTION = ITEMS.register("calamity_priest_potion", () -> {
            return new SimpleFoodItem(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(1.0F).build());
        });

         WINNER_POTION = ITEMS.register("winner_potion", () -> {
            return new SimpleFoodItem(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(1.0F).build());
        });

         MISFORTUNE_MAGE_POTION = ITEMS.register("misfortune_mage_potion", () -> {
            return new SimpleFoodItem(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(1.0F).build());
        });

        CHAOSWALKER_POTION  = ITEMS.register("chaoswalker_potion", () -> {
            return new SimpleFoodItem(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(1.0F).build());
        });

        SOOTHSAYER_POTION = ITEMS.register("soothsayer_potion", () -> {
            return new SimpleFoodItem(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(1.0F).build());
        });

        SNAKE_OF_MERCURY_POTION  = ITEMS.register("snake_of_mercury_potion", () -> {
            return new SimpleFoodItem(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(1.0F).build());
        });

        WOF_POTION = ITEMS.register("wof_potion", () -> {
            return new SimpleFoodItem(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(1.0F).build());
        });


        WORLDS_MEMORY = ITEMS.register("world_memory", () -> {
            return new GlintShard(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0.0F).effect(new MobEffectInstance(MobEffects.GLOWING, 100, 0), 1.0F).build());
        });
        REALITY_MEMORY = ITEMS.register("reality_memory", () -> {
            return new GlintShard(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0.0F).effect(new MobEffectInstance(MobEffects.GLOWING, 100, 0), 1.0F).build());
        });
        POWER_MEMORY = ITEMS.register("power_memory", () -> {
            return new GlintShard(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0.0F).effect(new MobEffectInstance(MobEffects.GLOWING, 100, 0), 1.0F).build());
        });
        HOPE_MEMORY = ITEMS.register("hope_memory", () -> {
            return new GlintShard(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0.0F).effect(new MobEffectInstance(MobEffects.GLOWING, 100, 0), 1.0F).build());
        });
        TIME_MEMORY = ITEMS.register("time_memory", () -> {
            return new GlintShard(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0.0F).effect(new MobEffectInstance(MobEffects.GLOWING, 100, 0), 1.0F).build());
        });
        SPACE_MEMORY = ITEMS.register("space_memory", () -> {
            return new GlintShard(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0.0F).effect(new MobEffectInstance(MobEffects.GLOWING, 100, 0), 1.0F).build());
        });

        MONKEY_SPIN = ITEMS.register("monkey_spin", MonkeySpin::new);




        RUBY_SHARD = ITEMS.register("ruby_fragment", () -> {
            return new GlintShard(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0.0F).effect(new MobEffectInstance(StellarEffects.RUBYBOOST.get(), 100, 0), 1.0F).build());
        });

        TOPAZ_SHARD = ITEMS.register("topaz_fragment", () -> {
            return new GlintShard(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0.0F).effect(new MobEffectInstance(StellarEffects.RUBYBOOST.get(), 100, 0), 1.0F).build());
        });

        CITRINE_SHARD = ITEMS.register("citrine_fragment", () -> {
            return new GlintShard(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0.0F).effect(new MobEffectInstance(StellarEffects.RUBYBOOST.get(), 100, 0), 1.0F).build());
        });

        JADE_SHARD = ITEMS.register("jade_fragment", () -> {
            return new GlintShard(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0.0F).effect(new MobEffectInstance(StellarEffects.RUBYBOOST.get(), 100, 0), 1.0F).build());
        });

        AQUA_SHARD = ITEMS.register("aquamarine_fragment", () -> {
            return new GlintShard(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0.0F).effect(new MobEffectInstance(StellarEffects.RUBYBOOST.get(), 100, 0), 1.0F).build());
        });

        SUGLITE_SHARD = ITEMS.register("sugilite_fragment", () -> {
            return new GlintShard(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0.0F).effect(new MobEffectInstance(StellarEffects.RUBYBOOST.get(), 100, 0), 1.0F).build());
        });

        SPINEL_SHARD = ITEMS.register("spinel_fragment", () -> {
            return new GlintShard(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0.0F).effect(new MobEffectInstance(StellarEffects.RUBYBOOST.get(), 100, 0), 1.0F).build());
        });

        ONYX_SHARD = ITEMS.register("onyx_fragment", () -> {
            return new GlintShard(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0.0F).effect(new MobEffectInstance(StellarEffects.RUBYBOOST.get(), 100, 0), 1.0F).build());
        });

        OPAL_SHARD = ITEMS.register("opal_fragment", () -> {
            return new GlintShard(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0.0F).effect(new MobEffectInstance(StellarEffects.RUBYBOOST.get(), 100, 0), 1.0F).build());
        });

        ELDRITCH_CORRUPTION = ITEMS.register("eldritch_corruption", () -> {
            return new GlintShard(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(0.0F).effect(new MobEffectInstance(MobEffects.WITHER, 100, 0), 1.0F).build());
        });









        MOLECULARESSENCE = ITEMS.register("molecular_essence", () -> {
            return new MiniEpessence(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(1.0F).effect(new MobEffectInstance(MobEffects.DIG_SPEED, 100, 0), 1.0F).effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED , 100, 0), 1.0F).build());
        });

        LUNARESSENCE = ITEMS.register("lunar_essence", () -> {
            return new LunarEpessence(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(1.0F).effect(new MobEffectInstance(MobEffects.DIG_SPEED, 100, 0), 1.0F).effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED , 100, 0), 1.0F).build());
        });

        PLANETARYESSENCE = ITEMS.register("planetary_essence", () -> {
            return new PlanetEpessence(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(1.0F).effect(new MobEffectInstance(MobEffects.DIG_SPEED, 100, 0), 1.0F).effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED , 100, 0), 1.0F).build());
        });

        SOLARESSENCE = ITEMS.register("solar_essence", () -> {
            return new SolarEpessence(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(1.0F).effect(new MobEffectInstance(MobEffects.DIG_SPEED, 100, 0), 1.0F).effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED , 100, 0), 1.0F).build());
        });

        GALACTICESSENCE = ITEMS.register("galactic_essence", () -> {
            return new GalacticEpessence(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(1.0F).effect(new MobEffectInstance(MobEffects.DIG_SPEED, 100, 0), 1.0F).effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED , 100, 0), 1.0F).build());
        });

        UNIVERSALESSENCE = ITEMS.register("universal_essence", () -> {
            return new UniverseEpessence(ItemTab.STELLARTAB, (new FoodProperties.Builder()).alwaysEat().nutrition(0).saturationMod(1.0F).effect(new MobEffectInstance(MobEffects.DIG_SPEED, 100, 0), 1.0F).effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED , 100, 0), 1.0F).build());
        });

         */
        EXCALIBUR = ITEMS.register("excalibur", () -> new Excalibur(TensuraToolTiers.HIHIIROKANE, 1, 0.2F, new Item.Properties().stacksTo(1).durability(100000)));

    }
}
