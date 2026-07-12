package com.radientfox.stellarprism.effects;

import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BloodBlockageEffect extends MobEffect {

    public static final String PREV_EP_TAG = "stellarprism_blood_blockage_prev_ep";

    private static final ResourceLocation MP_REGEN_ID =
            ResourceLocation.fromNamespaceAndPath("stellarprism", "blood_blockage_magicule_regen");
    private static final ResourceLocation AP_REGEN_ID =
            ResourceLocation.fromNamespaceAndPath("stellarprism", "blood_blockage_aura_regen");
    private static final ResourceLocation MAX_HP_ID =
            ResourceLocation.fromNamespaceAndPath("stellarprism", "blood_blockage_max_health");

    public BloodBlockageEffect() {
        super(MobEffectCategory.HARMFUL, 0x7A0014);
    }




    public void addAttributeModifiers(LivingEntity entity, AttributeMap map, int amplifier) {
        syncModifiers(entity, amplifier);
    }


    public void removeAttributeModifiers(LivingEntity entity, AttributeMap map, int amplifier) {
        remove(entity.getAttribute(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER), MP_REGEN_ID);
        remove(entity.getAttribute(TensuraAttributes.AURA_REGENERATION_MULTIPLIER), AP_REGEN_ID);
        remove(entity.getAttribute(Attributes.MAX_HEALTH), MAX_HP_ID);
        entity.getPersistentData().remove(PREV_EP_TAG);
        clampHealth(entity);
    }


    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        syncModifiers(entity, amplifier);
        clampHealth(entity);
        return true;
    }


    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 20 == 0;
    }

    public static int getHeartstones(int amplifier) {


        return Math.max(1, Math.min(11,  + 1));
    }

    public static boolean blocksHealing(LivingEntity entity, int heartstones) {
        if (heartstones >= 9) {
            return true;
        }
        if (heartstones >= 7) {
            return !hasInfiniteRegen(entity);
        }
        if (heartstones >= 4) {
            return !hasUltraOrInfiniteRegen(entity);
        }
        return false;
    }

    private void syncModifiers(LivingEntity entity, int amplifier) {
        int heartstones = getHeartstones(amplifier);

        double regenPenalty = regenPenalty(entity, heartstones);
        if (regenPenalty <= 0.0D) {
            remove(entity.getAttribute(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER), MP_REGEN_ID);
            remove(entity.getAttribute(TensuraAttributes.AURA_REGENERATION_MULTIPLIER), AP_REGEN_ID);
        } else {
            addOrReplace(
                    entity.getAttribute(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER),
                    MP_REGEN_ID,
                    -regenPenalty,
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            );
            addOrReplace(
                    entity.getAttribute(TensuraAttributes.AURA_REGENERATION_MULTIPLIER),
                    AP_REGEN_ID,
                    -regenPenalty,
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            );
        }

        syncHealthCapModifier(entity, heartstones);
    }

    private static double regenPenalty(LivingEntity entity, int heartstones) {
        if (heartstones >= 10) {
            return 1.0D;
        }
        if (heartstones >= 9) {
            return 1.0D;
        }
        if (heartstones >= 7) {
            return hasInfiniteRegen(entity) ? 0.0D : 1.0D;
        }
        if (heartstones >= 4) {
            return hasUltraOrInfiniteRegen(entity) ? 0.0D : 1.0D;
        }
        if (heartstones >= 3) {
            return 0.50D;
        }
        return 0.0D;
    }

    private static void syncHealthCapModifier(LivingEntity entity, int heartstones) {
        AttributeInstance health = entity.getAttribute(Attributes.MAX_HEALTH);
        if (health == null) {
            return;
        }

        health.removeModifier(MAX_HP_ID);
        double baseline = health.getValue();
        double amount = healthPenaltyAmount(baseline, heartstones);

        if (amount < 0.0D) {
            health.addOrReplacePermanentModifier(new AttributeModifier(MAX_HP_ID, amount, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    private static double healthPenaltyAmount(double baseline, int heartstones) {
        if (heartstones >= 10) {
            return Math.min(0.0D, 5.0D - baseline);
        }
        if (heartstones >= 9) {
            return Math.min(0.0D, 10.0D - baseline);
        }
        if (heartstones >= 8) {
            return -baseline * 0.50D;
        }
        if (heartstones >= 6) {
            return -baseline * 0.25D;
        }
        if (heartstones >= 5) {
            return -baseline * 0.10D;
        }
        return 0.0D;
    }

    private static boolean hasUltraOrInfiniteRegen(LivingEntity entity) {
        return hasUltraRegen(entity) || hasInfiniteRegen(entity);
    }

    private static boolean hasUltraRegen(LivingEntity entity) {
        return SkillUtils.hasSkill(entity, ExtraSkills.ULTRASPEED_REGENERATION.get());
    }

    private static boolean hasInfiniteRegen(LivingEntity entity) {
        return SkillUtils.hasSkill(entity, ExtraSkills.INFINITE_REGENERATION.get());
    }

    private static void clampHealth(LivingEntity entity) {
        double maxHealth = entity.getAttributeValue(Attributes.MAX_HEALTH);
        if (entity.getHealth() > maxHealth) {
            entity.setHealth((float) maxHealth);
        }
    }

    private static void addOrReplace(
            AttributeInstance instance,
            ResourceLocation id,
            double amount,
            AttributeModifier.Operation operation
    ) {
        if (instance == null) {
            return;
        }
        instance.addOrReplacePermanentModifier(new AttributeModifier(id, amount, operation));
    }

    private static void remove(AttributeInstance instance, ResourceLocation id) {
        if (instance != null) {
            instance.removeModifier(id);
        }
    }
}
