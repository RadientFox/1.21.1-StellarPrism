package com.radientfox.stellarprism.ability.Unique;

import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.util.AttributeHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.List;
import java.util.Map;

public class BoxerSkill extends Skill {

    private static final ResourceLocation BOXER = ResourceLocation.fromNamespaceAndPath("stellarprism", "boxer");

    private static final Map<String, String> RESISTANCE_EVOLUTIONS = Map.ofEntries(Map.entry("abnormal_condition_resistance", "abnormal_condition_nullification"),

            Map.entry("cold_resistance", "cold_nullification"), Map.entry("corrosion_resistance", "corrosion_nullification"),

            Map.entry("darkness_attack_resistance", "darkness_attack_nullification"),

            Map.entry("earth_attack_resistance", "earth_attack_nullification"), Map.entry("electricity_resistance", "electricity_nullification"),

            Map.entry("flame_attack_resistance", "flame_attack_nullification"),

            Map.entry("gravity_attack_resistance", "gravity_attack_nullification"),

            Map.entry("heat_resistance", "heat_nullification"), Map.entry("holy_attack_resistance", "holy_attack_nullification"),

            Map.entry("light_attack_resistance", "light_attack_nullification"),

            Map.entry("magic_resistance", "magic_nullification"),

            Map.entry("pain_resistance", "pain_nullification"), Map.entry("paralysis_resistance", "paralysis_nullification"), Map.entry("physical_attack_resistance", "physical_attack_nullification"), Map.entry("pierce_resistance", "pierce_nullification"), Map.entry("poison_resistance", "poison_nullification"),

            Map.entry("spatial_attack_resistance", "spatial_attack_nullification"), Map.entry("spiritual_attack_resistance", "spiritual_attack_nullification"),

            Map.entry("thermal_fluctuation_resistance", "thermal_fluctuation_nullification"),

            Map.entry("water_attack_resistance", "water_attack_nullification"), Map.entry("wind_attack_resistance", "wind_attack_nullification"));
    private static final ResourceLocation BLOCK_SPEED = ResourceLocation.fromNamespaceAndPath("stellarprism", "boxer_block_speed");
    private static final ResourceLocation BLOCK_ATTACK_SPEED = ResourceLocation.fromNamespaceAndPath("stellarprism", "boxer_block_attack_speed");
    private static final ResourceLocation BLOCK_MELEE_DODGE = ResourceLocation.fromNamespaceAndPath("stellarprism", "boxer_block_melee_dodge");
    private static final ResourceLocation BLOCK_PROJECTILE_DODGE = ResourceLocation.fromNamespaceAndPath("stellarprism", "boxer_block_projectile_dodge");
    private static final String BLOCKING_ACTIVE = "BoxerBlocking";

    public BoxerSkill() {
        super(Skill.SkillType.UNIQUE);
    }

    @Override
    public MutableComponent getSkillDescription() {
        return Component.literal("Get ready to study hard, Mac.");
    }

    public static void evolveResistance(LivingEntity entity, ManasSkillInstance resistanceInstance) {
        if (resistanceInstance == null || resistanceInstance.getSkill() == null) {
            return;
        }

        if (!resistanceInstance.isMastered(entity)) {
            return;
        }

        ResourceLocation resistanceId = resistanceInstance.getSkill().getRegistryName();

        if (resistanceId == null) {
            return;
        }

        String nullificationPath = RESISTANCE_EVOLUTIONS.get(resistanceId.getPath());

        if (nullificationPath == null) {
            return;
        }

        ResourceLocation nullificationId = ResourceLocation.fromNamespaceAndPath(resistanceId.getNamespace(), nullificationPath);

        ManasSkill nullificationSkill = SkillAPI.getSkillRegistry().get(nullificationId);

        if (nullificationSkill == null) {
            return;
        }

        if (SkillAPI.getSkillsFrom(entity).getSkill(nullificationSkill).isPresent()) {
            return;
        }

        TensuraSkillInstance nullificationInstance = new TensuraSkillInstance(nullificationSkill);

        nullificationInstance.getOrCreateTag().putBoolean("NoMagiculeCost", true);

        SkillHelper.learnSkill(entity, nullificationInstance);
    }

    @Override
    public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
        return 166000.0F;
    }

    @Override
    public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
        return true;
    }

    @Override
    public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
        AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.RESISTANCE_DEGRADATION, BOXER, 1.0D, AttributeModifier.Operation.ADD_VALUE);
        AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.DODGE_NEGATE_CHANCE, BOXER, 100.0F, AttributeModifier.Operation.ADD_VALUE);
        AttributeInstance melee = entity.getAttribute(TensuraAttributes.AUTO_MELEE_DODGE_CHANCE);
        if (melee != null) {
            melee.addOrReplacePermanentModifier(new AttributeModifier(BOXER, 80.0D, AttributeModifier.Operation.ADD_VALUE));
        }

        AttributeInstance projectile = entity.getAttribute(TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE);
        if (projectile != null) {
            projectile.addOrReplacePermanentModifier(new AttributeModifier(BOXER, 60.0D, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    @Override
    public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
        AttributeHelper.removeAttribute(entity, TensuraAttributes.RESISTANCE_DEGRADATION, BOXER);
        AttributeHelper.removeAttribute(entity, TensuraAttributes.DODGE_NEGATE_CHANCE, BOXER);
        AttributeInstance melee = entity.getAttribute(TensuraAttributes.AUTO_MELEE_DODGE_CHANCE);
        if (melee != null) {
            melee.removeModifier(BOXER);
        }

        AttributeInstance projectile = entity.getAttribute(TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE);
        if (projectile != null) {
            projectile.removeModifier(BOXER);
        }
    }

    @Override
    public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
        return true;
    }

    @Override
    public void onTick(ManasSkillInstance instance, LivingEntity entity) {
        super.onTick(instance, entity);

        if (!this.isInSlot(entity, instance)) {
            removeBlockingAttributes(entity);
            instance.getOrCreateTag().putBoolean(BLOCKING_ACTIVE, false);
        }

        Skills skills = SkillAPI.getSkillsFrom(entity);

        for (ManasSkillInstance skill : List.copyOf(skills.getLearnedSkills())) {
            if (skill == null || skill.getSkill() == null) {
                continue;
            }

            evolveResistance(entity, skill);
        }

        if (entity.getMainHandItem().isEmpty()) {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 1, false, false, false));

            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 0, false, false, false));
        } else {
            entity.removeEffect(MobEffects.MOVEMENT_SPEED);
            entity.removeEffect(MobEffects.DAMAGE_RESISTANCE);
        }
    }

    @Override
    public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity attacker, LivingEntity target, DamageSource source, Changeable<Float> amount) {
        if (!this.isInSlot(attacker, instance)) {
            return true;
        }

        if (attacker.getMainHandItem().isEmpty()) {
            amount.set(amount.get() * 8.0F);
        } else {
            amount.set(amount.get() * 0.15F);
        }

        return true;
    }

    @Override
    public int getModes(ManasSkillInstance instance) {
        return 1;
    }

    @Override
    public String getModeId(ManasSkillInstance instance, int mode) {
        return switch (mode) {
            case 0 -> "boxer.blocking";
            default -> super.getModeId(instance, mode);
        };
    }


    @Override
    public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
        if (mode != 0) {
            removeBlockingAttributes(entity);
            instance.getOrCreateTag().putBoolean(BLOCKING_ACTIVE, false);
            return false;
        }

        AttributeInstance speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);

        if (speed != null) {
            speed.addOrReplacePermanentModifier(
                    new AttributeModifier(
                            BLOCK_SPEED,
                            -0.8D,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                    )
            );
        }

        AttributeInstance attackSpeed = entity.getAttribute(Attributes.ATTACK_SPEED);

        if (attackSpeed != null) {
            attackSpeed.addOrReplacePermanentModifier(
                    new AttributeModifier(
                            BLOCK_ATTACK_SPEED,
                            -1.0D,
                            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                    )
            );
        }

        AttributeInstance melee = entity.getAttribute(TensuraAttributes.AUTO_MELEE_DODGE_CHANCE);

        if (melee != null) {
            melee.addOrReplacePermanentModifier(
                    new AttributeModifier(
                            BLOCK_MELEE_DODGE,
                            20.0D,
                            AttributeModifier.Operation.ADD_VALUE
                    )
            );
        }

        AttributeInstance projectile = entity.getAttribute(TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE);

        if (projectile != null) {
            projectile.addOrReplacePermanentModifier(
                    new AttributeModifier(
                            BLOCK_PROJECTILE_DODGE,
                            40.0D,
                            AttributeModifier.Operation.ADD_VALUE
                    )
            );
        }

        instance.getOrCreateTag().putBoolean(BLOCKING_ACTIVE, true);

        return true;
    }

    @Override
    public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
        if (mode == 0) {
            removeBlockingAttributes(entity);
        }
    }

    private void removeBlockingAttributes(LivingEntity entity) {
        AttributeInstance speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null) {
            speed.removeModifier(BLOCK_SPEED);
        }

        AttributeInstance attackSpeed = entity.getAttribute(Attributes.ATTACK_SPEED);
        if (attackSpeed != null) {
            attackSpeed.removeModifier(BLOCK_ATTACK_SPEED);
        }

        AttributeInstance melee = entity.getAttribute(TensuraAttributes.AUTO_MELEE_DODGE_CHANCE);
        if (melee != null) {
            melee.removeModifier(BLOCK_MELEE_DODGE);
        }

        AttributeInstance projectile = entity.getAttribute(TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE);
        if (projectile != null) {
            projectile.removeModifier(BLOCK_PROJECTILE_DODGE);
        }
    }

}
