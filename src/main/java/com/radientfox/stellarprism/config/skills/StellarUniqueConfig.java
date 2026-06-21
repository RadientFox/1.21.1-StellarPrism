package com.radientfox.stellarprism.config.skills;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;

public class StellarUniqueConfig extends ManasConfig {

    public static Spinel Spinel = new Spinel();
    public StellarUniqueConfig.Jade Jade = new StellarUniqueConfig.Jade();
    public StellarUniqueConfig.Nimue Nimue = new StellarUniqueConfig.Nimue();
    public StellarUniqueConfig.AmuletofPower AmuletofPower = new StellarUniqueConfig.AmuletofPower();
    public StellarUniqueConfig.Prism Prism = new StellarUniqueConfig.Prism();
    public StellarUniqueConfig.AddandSubtract AddandSubtract = new StellarUniqueConfig.AddandSubtract();
    public StellarUniqueConfig.VoidPriestess VoidPriestess = new StellarUniqueConfig.VoidPriestess();


    public StellarUniqueConfig() {
    }

    public String getFileName() {
        return "stellarprism/ability/skill/unique_config";
    }

    public static class Spinel extends ManasSubConfig {
        @Comment("Magicule Acquirement Cost.")
        public double mpAcquirement = 60_000.0;
        @Comment("Skill Mastery Points.")
        public double masteryPoints = 2_500;
        @Comment("Enable In Slot Severance Immunity.")
        public boolean severanceImmunity = true;
        @Comment("Damage multiplier with Spatial Domination.")
        public double spaceDom = 2;
        @Comment("Damage multiplier with Molecular Manipulation.")
        public double molecManip = 2.5;
        @Comment("Damage multiplier with Primordial Domination.")
        public double primodialDom = 3;
        @Comment("Enable Space Transform obtainment.")
        public boolean spaceTransform = true;
        @Comment("Aura cost for Space Cutter.")
        public double apSpaceCutter = 10_000;
        @Comment("Damage for Space Cutter.")
        public double dmgSpaceCutter = 75;
        @Comment("Damage for Space Cutter on mastery.")
        public double masterydmgSpaceCutter = 250;
        @Comment("Cooldown for Space Cutter.")
        public double spaceCutterCooldown = 5.0;
        @Comment("Aura cost for Pocket Storage.")
        public double apPocketStorage = 1_000;
        @Comment("The number of slots of the Pocket Storage.")
        public int spatialSlots = 54;
        @Comment("The maximum stack size of items in the Pocket Storage.")
        public int spatialStack = 200;
        @Comment("The maximum stack size of items in the Pocket Storage when mastered.")
        public int spatialStackMastered = 500;
        @Comment("Needs Spirit for Spatial Aura.")
        public boolean needsSpirit = true;
        @Comment("Number of battlewills needed for Spatial Aura.")
        public int spaitialBattlewills = 5;
        @Comment("Cooldown for Spatial Aura.")
        public double spaceAuraCooldown = 160.0;

        public Spinel() {
        }
    }



    public static class Jade extends ManasSubConfig {

        public Jade() {
        }
        @Comment("Magicule Acquirement Cost.")
        public double mpAcquirement = 60_000.0;
        @Comment("Enable Wind Transform obtainment.")
        public boolean windTransform = true;
        @Comment("The Wind Damage Boost when activated Manipulation.")
        public double windDom = 2.0F;
        @Comment("Damage multiplier with Molecular Manipulation.")
        public double moleWind = 3.0;
        @Comment("Damage multiplier with Primordial Domination.")
        public double primodialDom = 4;
        @Comment("Mp cost for Spiral Winds.")
        public double spiralWindMP = 10_000;
        @Comment("Mp cost for Empowering Winds.")
        public double empoweringWindMP = 50_000;
        @Comment("The magic damage of the wind tornado.")
        public float magicDamage = 50.0F;
        @Comment("The magic damage of the wind tornado when mastered.")
        public float magicDamageMastered = 100.0F;
        @Comment("The wind damage of the wind tornado.")
        public float windDamage = 200.0F;
        @Comment("The wind damage of the wind tornado when mastered.")
        public float windDamageMastered = 250.0F;
        @Comment("The damage radius of the wind tornado.")
        public float hitRadius = 3.0F;
        @Comment("The damage radius of the wind tornado when mastered.")
        public float hitRadiusMastered = 4.0F;
        @Comment("The delay in tick of the wind tornado before exploding.")
        public int burstDelay = 20;
        @Comment("The pull force multiplier of the wind tornado when mastered.")
        public float pullForce = 0.3F;
        @Comment("The number of wind blades that the wind tornado release on explosion when mastered.")
        public int bladeNumber = 10;
        @Comment("The magic damage of each wind blade that the wind tornado release on explosion when mastered.")
        public float bladeDamage = 50.0F;
        @Comment("Cooldown for Spiral Winds.")
        public double spiralWindsCooldown = 5.0;
        @Comment("Cooldown for Empowering Wind.")
        public double empoweringWindCooldown = 160.0;
        @Comment("Battlewills needed for second mode.")
        public int windBattlewills = 5;
        @Comment("Needs Spirit for Wind Aura.")
        public boolean needsSpirit = true;
    }



    public static class Nimue extends ManasSubConfig {

        public Nimue() {
        }
        @Comment("Magicule Acquirement Cost.")
        public double mpAcquirement = 100_000.0;
        @Comment("Skill Mastery Points.")
        public double masteryPoints = 2_500;
        @Comment("Cooldown for Purifying Water in seconds.")
        public double purifyingWaterCooldown = 10.0;
        @Comment("Cooldown for Lilypad Step in seconds.")
        public double lilypadStepCooldown = 15.0;
    }


















    public static class AmuletofPower extends ManasSubConfig {
        @Comment("Magicule Acquirement Cost.")
        public double mpAcquirement = 100_000.0;
        @Comment("Skill Mastery Points.")
        public double masteryPoints = 2_000.0;
        @Comment("Passive Damage Multiplier.")
        public double powerEnhanced = 1.5;
        @Comment("Passive Damage Multiplier Mastered.")
        public double powerEnhancedMastered = 3.0;
        @Comment("Magicule cost for Boost.(Scales with Multiplier)")
        public double mpBoost = 1_000;
        @Comment("Boost Damage Multiplier Stage 1.")
        public double Boost1 = 2.0;
        @Comment("Boost Damage Multiplier Stage 2.")
        public double Boost2 = 4.0;
        @Comment("Boost Damage Multiplier Stage 3.")
        public double Boost3 = 6.0;
        @Comment("Boost Damage Multiplier Stage 4.")
        public double Boost4 = 8.0;
        @Comment("Boost Damage Multiplier Stage 5.")
        public double Boost5 = 10.0;
        @Comment("Boost Damage Duration in ticks.")
        public double BoostTicks = 3_600.0;
        @Comment("Magicule cost for Power Shot.")
        public double mpPowerShot = 5_000;
        @Comment("Magicule cost for Power Shot on mastery.")
        public double mpPowerShotMastered = 10_000;
        @Comment("Mastery Percent for Power Shot.")
        public double powerShotUnlock = 0.5;
        @Comment("Cooldown in ticks for Power Shot.")
        public double powerShotCooldown = 2_400.0;
        @Comment("Power Shot Damage Multiplier.")
        public double powerShotMult = 2.0;


        public AmuletofPower() {
        }
    }



    public static class Prism extends ManasSubConfig {
        @Comment("Magicule Acquirement Cost.")
        public double mpAcquirement = 77_777.0;
        @Comment("Skill Mastery Points.")
        public double masteryPoints = 7_777;

    public Prism() {
    }
    }


    public static class AddandSubtract extends ManasSubConfig {
        @Comment("Magicule Acquirement Cost.")
        public double mpAcquirement = 150_000.0;
        @Comment("Skill Mastery Points.")
        public double masteryPoints = 1_234.0;
        @Comment("Auto Subtraction Damage.")
        public double autoSubDmg = 25.0;
        @Comment("Auto Subtraction Damage on Mastery.")
        public double autoSubDmgMastery = 75.0;
        @Comment("Add armor points.")
        public double addArmor = 25.0;
        @Comment("Add armor points on mastery.")
        public double addArmorMastery = 50.0;
        @Comment("Add HP increase.")
        public double addHP = 0.1;
        @Comment("Add SHP increase.")
        public double addSHP = 0.1;
        @Comment("Auto Add Stat increase increase.")
        public double addStatincrease = 0.2;
        @Comment("Subtract Damage.")
        public double subtractDMG = 100.0;
        @Comment("Subtract Cooldown.")
        public double subtractCooldown = 5.0;
        @Comment("Subtract Damage Mastery.")
        public double subtractDMGMastery = 250.0;
        @Comment("Subtract Cooldown Mastery.")
        public double subtractCooldownMastery = 2.0;
        @Comment("Negative Addition Damage .")
        public double negativeAddition = 400.0;


    public AddandSubtract() {
    }
    }



    public static class VoidPriestess  extends ManasSubConfig {
        @Comment("Magicule Acquirement Cost.")
        public double mpAcquirement = 150_000.0;
        @Comment("Skill Mastery Points.")
        public double masteryPoints = 1_500.0;
        @Comment("Void Damage increase for user per subordinate.")
        public double increaseSelf = 3.0;
        @Comment("Void Damage increase for user per subordinate on mastery.")
        public double increaseSelfMastered = 5.0;
        @Comment("Void Damage increase for allies per subordinate.")
        public double increaseAllies = 1.0;
        @Comment("Void Damage increase for allies per subordinate.")
        public double increaseAlliesMastered = 3.0;
        @Comment("Charm MP cost.")
        public double charmCost = 30_000.0;
        @Comment("Charm Duration.")
        public double charmTicks = 12_000.0;
        @Comment("Charm Cooldown.")
        public double charmCooldown = 12_000.0;

    public VoidPriestess() {
    }
    }






}
