package com.radientfox.stellarprism.config.races.fox;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.tensura.config.race.BeastfolkConfig;
import io.github.manasmods.tensura.config.race.RaceConfig;

import javax.annotation.processing.Generated;

public class FoxRaceConfig extends ManasConfig {


    public FoxRaceConfig.Kit Kit = new FoxRaceConfig.Kit();
    public FoxRaceConfig.ElementalKit ElementalKit = new FoxRaceConfig.ElementalKit();
    public FoxRaceConfig.ElementalFox ElementalFox = new FoxRaceConfig.ElementalFox();
    public FoxRaceConfig.ElementalKitsune ElementalKitsune = new FoxRaceConfig.ElementalKitsune();
    public FoxRaceConfig.ElementalKitsuneSpirit ElementalKitsuneSpirit = new FoxRaceConfig.ElementalKitsuneSpirit();
    public FoxRaceConfig.AttunedKitsuneSpirit AttunedKitsuneSpirit = new FoxRaceConfig.AttunedKitsuneSpirit();

    public FoxRaceConfig() {
    }

    public String getFileName() {
        return "stellarprism/race/fox_config";
    }

    public static class Kit extends RaceConfig.Default {
        @Comment("Minimal aura.")
        public double minAura = 3000.0;
        @Comment("Maximum aura.")
        public double maxAura = 5000.0;
        @Comment("Minimal magicule.")
        public double minMagicule = 3000.0;
        @Comment("Maximum magicule.")
        public double maxMagicule = 6000.0;
        @Comment("Bonus Size.")
        public double size = -1.4;
        @Comment("Bonus Max Health.")
        public double maxHealth = -10.0;
        @Comment("Bonus Max Spiritual Health.")
        public double maxSpiritualHealth = 50.0;
        @Comment("Bonus Attack Damage.")
        public double attack = -0.5;
        @Comment("Bonus Attack Speed.")
        public double attackSpeed = -3.0;
        @Comment("Bonus Knockback Resistance.")
        public double knockbackResistance = 0.0;
        @Comment("Bonus Movement Speed.")
        public double movementSpeed = 0.03;
        @Comment("Bonus Swimming Speed Multiplier.")
        public double swimSpeed = 0.5;
        @Comment("Bonus Jump Strength.")
        public double jumpStrength = 0.33;
        public Kit() {
        }


        public double getMinAura() {
            return this.minAura;
        }

        public double getMaxAura() {
            return this.maxAura;
        }

        public double getMinMagicule() {
            return this.minMagicule;
        }

        public double getMaxMagicule() {
            return this.maxMagicule;
        }

        public double getSize() {
            return this.size;
        }

        public double getMaxHealth() {
            return this.maxHealth;
        }

        public double getMaxSpiritualHealth() {
            return this.maxSpiritualHealth;
        }

        public double getAttack() {
            return this.attack;
        }

        public double getAttackSpeed() {
            return this.attackSpeed;
        }

        public double getKnockbackResistance() {
            return this.knockbackResistance;
        }

        public double getMovementSpeed() {
            return this.movementSpeed;
        }

        public double getSwimSpeed() {
            return this.swimSpeed;
        }
    }



    public static class ElementalKit extends RaceConfig.Default {
        @Comment("Minimal aura.")
        public double minAura = 9000.0;
        @Comment("Maximum aura.")
        public double maxAura = 12000.0;
        @Comment("Minimal magicule.")
        public double minMagicule = 12000.0;
        @Comment("Maximum magicule.")
        public double maxMagicule = 15000.0;
        @Comment("Bonus Size.")
        public double size = -1.3;
        @Comment("Bonus Max Health.")
        public double maxHealth = -5.0;
        @Comment("Bonus Max Spiritual Health.")
        public double maxSpiritualHealth = 170.0;
        @Comment("Bonus Attack Damage.")
        public double attack = 0.0;
        @Comment("Bonus Attack Speed.")
        public double attackSpeed = -2.5;
        @Comment("Bonus Knockback Resistance.")
        public double knockbackResistance = 0.0;
        @Comment("Bonus Movement Speed.")
        public double movementSpeed = 0.04;
        @Comment("Bonus Swimming Speed Multiplier.")
        public double swimSpeed = 0.5;
        @Comment("Bonus Jump Strength.")
        public double jumpStrength = 2.0;
        @Comment("Elemental Essence Requirement.")
        public double elementalEssence = 5.0;
        @Comment("EP Requirement.")
        public double epRequirement = 15_000.0;


        public ElementalKit() {
        }


        public double getMinAura() {
            return this.minAura;
        }

        public double getMaxAura() {
            return this.maxAura;
        }

        public double getMinMagicule() {
            return this.minMagicule;
        }

        public double getMaxMagicule() {
            return this.maxMagicule;
        }

        public double getSize() {
            return this.size;
        }

        public double getMaxHealth() {
            return this.maxHealth;
        }

        public double getMaxSpiritualHealth() {
            return this.maxSpiritualHealth;
        }

        public double getAttack() {
            return this.attack;
        }

        public double getAttackSpeed() {
            return this.attackSpeed;
        }

        public double getKnockbackResistance() {
            return this.knockbackResistance;
        }

        public double getMovementSpeed() {
            return this.movementSpeed;
        }

        public double getSwimSpeed() {
            return this.swimSpeed;
        }
    }



    public static class ElementalFox extends RaceConfig.Default {
        @Comment("Minimal aura.")
        public double minAura = 9000.0;
        @Comment("Maximum aura.")
        public double maxAura = 12000.0;
        @Comment("Minimal magicule.")
        public double minMagicule = 12000.0;
        @Comment("Maximum magicule.")
        public double maxMagicule = 15000.0;
        @Comment("Bonus Size.")
        public double size = -0.8;
        @Comment("Bonus Max Health.")
        public double maxHealth = 30.0;
        @Comment("Bonus Max Spiritual Health.")
        public double maxSpiritualHealth = 380.0;
        @Comment("Bonus Attack Damage.")
        public double attack = 1.0;
        @Comment("Bonus Attack Speed.")
        public double attackSpeed = -1.0;
        @Comment("Bonus Knockback Resistance.")
        public double knockbackResistance = 0.0;
        @Comment("Bonus Movement Speed.")
        public double movementSpeed = 0.05;
        @Comment("Bonus Swimming Speed Multiplier.")
        public double swimSpeed = 0.5;
        @Comment("Bonus Jump Strength.")
        public double jumpStrength = 2.5;
        @Comment("Elemental Essence Requirement.")
        public double elementalEssence = 20.0;
        @Comment("EP Requirement.")
        public double epRequirement = 175_000.0;

        public ElementalFox() {
        }


        public double getMinAura() {
            return this.minAura;
        }

        public double getMaxAura() {
            return this.maxAura;
        }

        public double getMinMagicule() {
            return this.minMagicule;
        }

        public double getMaxMagicule() {
            return this.maxMagicule;
        }

        public double getSize() {
            return this.size;
        }

        public double getMaxHealth() {
            return this.maxHealth;
        }

        public double getMaxSpiritualHealth() {
            return this.maxSpiritualHealth;
        }

        public double getAttack() {
            return this.attack;
        }

        public double getAttackSpeed() {
            return this.attackSpeed;
        }

        public double getKnockbackResistance() {
            return this.knockbackResistance;
        }

        public double getMovementSpeed() {
            return this.movementSpeed;
        }

        public double getSwimSpeed() {
            return this.swimSpeed;
        }
    }



    public static class ElementalKitsune extends RaceConfig.Default {
        @Comment("Minimal aura.")
        public double minAura = 9000.0;
        @Comment("Maximum aura.")
        public double maxAura = 12000.0;
        @Comment("Minimal magicule.")
        public double minMagicule = 12000.0;
        @Comment("Maximum magicule.")
        public double maxMagicule = 15000.0;
        @Comment("Bonus Size.")
        public double size = -0.8;
        @Comment("Bonus Max Health.")
        public double maxHealth = 235.0;
        @Comment("Bonus Max Spiritual Health.")
        public double maxSpiritualHealth = 4500.0;
        @Comment("Bonus Attack Damage.")
        public double attack = 3.0;
        @Comment("Bonus Attack Speed.")
        public double attackSpeed = -1.0;
        @Comment("Bonus Knockback Resistance.")
        public double knockbackResistance = 0.5;
        @Comment("Bonus Movement Speed.")
        public double movementSpeed = 0.05;
        @Comment("Bonus Swimming Speed Multiplier.")
        public double swimSpeed = 0.7;
        @Comment("Bonus Jump Strength.")
        public double jumpStrength = 3.0;
        @Comment("Elemental Essence Requirement.")
        public double elementalEssence = 50.0;
        @Comment("EP Requirement.")
        public double epRequirement = 9_400_000.0;

        public ElementalKitsune() {
        }


        public double getMinAura() {
            return this.minAura;
        }

        public double getMaxAura() {
            return this.maxAura;
        }

        public double getMinMagicule() {
            return this.minMagicule;
        }

        public double getMaxMagicule() {
            return this.maxMagicule;
        }

        public double getSize() {
            return this.size;
        }

        public double getMaxHealth() {
            return this.maxHealth;
        }

        public double getMaxSpiritualHealth() {
            return this.maxSpiritualHealth;
        }

        public double getAttack() {
            return this.attack;
        }

        public double getAttackSpeed() {
            return this.attackSpeed;
        }

        public double getKnockbackResistance() {
            return this.knockbackResistance;
        }

        public double getMovementSpeed() {
            return this.movementSpeed;
        }

        public double getSwimSpeed() {
            return this.swimSpeed;
        }
    }


    public static class ElementalKitsuneSpirit extends RaceConfig.Default {
        @Comment("Minimal aura.")
        public double minAura = 9000.0;
        @Comment("Maximum aura.")
        public double maxAura = 12000.0;
        @Comment("Minimal magicule.")
        public double minMagicule = 12000.0;
        @Comment("Maximum magicule.")
        public double maxMagicule = 15000.0;
        @Comment("Bonus Size.")
        public double size = -0.5;
        @Comment("Bonus Max Health.")
        public double maxHealth = 630.0;
        @Comment("Bonus Max Spiritual Health.")
        public double maxSpiritualHealth = 7000.0;
        @Comment("Bonus Attack Damage.")
        public double attack = 4.0;
        @Comment("Bonus Attack Speed.")
        public double attackSpeed = -0.5;
        @Comment("Bonus Knockback Resistance.")
        public double knockbackResistance = 0.5;
        @Comment("Bonus Movement Speed.")
        public double movementSpeed = 0.05;
        @Comment("Bonus Swimming Speed Multiplier.")
        public double swimSpeed = 0.8;
        @Comment("Bonus Jump Strength.")
        public double jumpStrength = 3.5;

        public ElementalKitsuneSpirit() {
        }


        public double getMinAura() {
            return this.minAura;
        }

        public double getMaxAura() {
            return this.maxAura;
        }

        public double getMinMagicule() {
            return this.minMagicule;
        }

        public double getMaxMagicule() {
            return this.maxMagicule;
        }

        public double getSize() {
            return this.size;
        }

        public double getMaxHealth() {
            return this.maxHealth;
        }

        public double getMaxSpiritualHealth() {
            return this.maxSpiritualHealth;
        }

        public double getAttack() {
            return this.attack;
        }

        public double getAttackSpeed() {
            return this.attackSpeed;
        }

        public double getKnockbackResistance() {
            return this.knockbackResistance;
        }

        public double getMovementSpeed() {
            return this.movementSpeed;
        }

        public double getSwimSpeed() {
            return this.swimSpeed;
        }
    }

    public static class AttunedKitsuneSpirit extends RaceConfig.Default {
        @Comment("Minimal aura.")
        public double minAura = 9000.0;
        @Comment("Maximum aura.")
        public double maxAura = 12000.0;
        @Comment("Minimal magicule.")
        public double minMagicule = 12000.0;
        @Comment("Maximum magicule.")
        public double maxMagicule = 15000.0;
        @Comment("Bonus Size.")
        public double size = -0.4;
        @Comment("Bonus Max Health.")
        public double maxHealth = 1330.0;
        @Comment("Bonus Max Spiritual Health.")
        public double maxSpiritualHealth = 15000.0;
        @Comment("Bonus Attack Damage.")
        public double attack = 5.0;
        @Comment("Bonus Attack Speed.")
        public double attackSpeed = 0.0;
        @Comment("Bonus Knockback Resistance.")
        public double knockbackResistance = 1.0;
        @Comment("Bonus Movement Speed.")
        public double movementSpeed = 0.06;
        @Comment("Bonus Swimming Speed Multiplier.")
        public double swimSpeed = 0.8;
        @Comment("Bonus Jump Strength.")
        public double jumpStrength = 4.0;

        public AttunedKitsuneSpirit() {
        }


        public double getMinAura() {
            return this.minAura;
        }

        public double getMaxAura() {
            return this.maxAura;
        }

        public double getMinMagicule() {
            return this.minMagicule;
        }

        public double getMaxMagicule() {
            return this.maxMagicule;
        }

        public double getSize() {
            return this.size;
        }

        public double getMaxHealth() {
            return this.maxHealth;
        }

        public double getMaxSpiritualHealth() {
            return this.maxSpiritualHealth;
        }

        public double getAttack() {
            return this.attack;
        }

        public double getAttackSpeed() {
            return this.attackSpeed;
        }

        public double getKnockbackResistance() {
            return this.knockbackResistance;
        }

        public double getMovementSpeed() {
            return this.movementSpeed;
        }

        public double getSwimSpeed() {
            return this.swimSpeed;
        }
    }

}
