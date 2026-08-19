package com.radientfox.stellarprism.ability.Unique;

import com.radientfox.stellarprism.config.skills.StellarUniqueConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class DianlanhuaSkill extends Skill {

    private static final ResourceLocation DIANLANHUA_DODGE = ResourceLocation.fromNamespaceAndPath("stellarprism", "dianlanhua_dodge");
    private static final StellarUniqueConfig.Dianlanhua CONFIG = ConfigRegistry.getConfig(StellarUniqueConfig.class).Dianlanhua;


    public DianlanhuaSkill() {
        super(Skill.SkillType.UNIQUE);
    }

    @Override
    public MutableComponent getSkillDescription() {
        return Component.literal("Envy is key.");
    }

    @Override
    public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
        return 150000;
    }

    @Override
    public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
        return true;
    }

    @Override
    public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
        return true;
    }

    @Override
    public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
        if (entity instanceof Player player) {
            if (!player.getAbilities().mayfly) {
                player.getAbilities().mayfly = true;
            }

            player.getAbilities().setFlyingSpeed(0.1F);
            player.onUpdateAbilities();
        }
    }

    @Override
    public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
        if (entity instanceof Player player) {
            player.getAbilities().mayfly = false;
            player.getAbilities().flying = false;
            player.onUpdateAbilities();
        }

        removeDarknessDodge(entity);
    }

    @Override
    public void onTick(ManasSkillInstance instance, LivingEntity entity) {
        if (!instance.isToggled()) {
            removeDarknessDodge(entity);
            return;
        }

        if (entity.level().isClientSide) {
            return;
        }

        boolean isDark = isInDarkness(entity);

        if (isDark) {
            applyDarknessDodge(instance, entity);
        } else {
            removeDarknessDodge(entity);
        }
    }

    @Override
    public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
        if (!instance.isToggled()) {
            return true;
        }

        if (isInDarkness(owner)) {
            amount.set(amount.get() * 1.5F);
        }

        return true;
    }

    private boolean isInDarkness(LivingEntity entity) {
        return entity.level().getMaxLocalRawBrightness(entity.blockPosition()) < 8;
    }

    private void applyDarknessDodge(ManasSkillInstance instance, LivingEntity entity) {
        var dodge = entity.getAttribute(TensuraAttributes.AUTO_MELEE_DODGE_CHANCE);

        if (dodge == null) {
            return;
        }

        double dodgeChance = instance.isMastered(entity) ? 40.0D : 30.0D;

        dodge.addOrReplacePermanentModifier(new net.minecraft.world.entity.ai.attributes.AttributeModifier(DIANLANHUA_DODGE, dodgeChance, net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADD_VALUE));
    }

    private void removeDarknessDodge(LivingEntity entity) {
        var dodge = entity.getAttribute(TensuraAttributes.AUTO_MELEE_DODGE_CHANCE);

        if (dodge != null) {
            dodge.removeModifier(DIANLANHUA_DODGE);
        }
    }

    @Override
    public int getModes(ManasSkillInstance instance) {
        return 4;
    }

    @Override
    public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
        if (reverse) return (mode == 0) ? 3 : (mode - 1);

        return (mode == 3) ? 0 : (mode + 1);
    }

    @Override
    public String getModeId(ManasSkillInstance instance, int mode) {
        return switch (mode) {
            case 0 -> "dianlanhua.toxic_selection";
            case 1 -> "dianlanhua.shade_step";
            case 2 -> "dianlanhua.gale_motion";
            case 3 -> "dianlanhua.toxin_queen";
            default -> super.getModeId(instance, mode);
        };
    }

    @Override
    public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
        return switch (mode) {
            case 0 -> CONFIG.toxicSelectionCost;
            case 1 -> CONFIG.shadeStepCost;
            case 2 -> CONFIG.galeMotionCost;
            case 3 -> CONFIG.toxinQueenCost;
            default -> 0.0D;
        };
    }

    @Override
    public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {

        switch (mode) {
//
//            case 0:
//                toxicSelectionMode(instance, entity, entity.level());
//                instance.setCoolDown(CONFIG.toxicSelectionCooldown, 0);
//                break;
//
//            case 1:
//                spineWhipMode(instance, entity, entity.level());
//                instance.setCoolDown(CONFIG.spineWhipCooldown, 1);
//                break;

            case 2:
                shadeStepMode(instance, entity, entity.level());
                instance.setCoolDown(CONFIG.shadeStepCooldown, 2);
                break;

//            case 3:
//                godOfSacrificeMode(instance, entity, entity.level());
//                instance.setCoolDown(CONFIG.godOfSacrificeCooldown, 3);
//                break;
        }
    }

    private void shadeStepMode(ManasSkillInstance instance, LivingEntity entity, Level level) {
        if (!(entity instanceof Player player)) return;
        if (level.isClientSide) return;

        CompoundTag tag = instance.getOrCreateTag();
        boolean isConcealed = tag.getBoolean("ShadeStep");

        if (isConcealed) {
            tag.putBoolean("ShadeStep", false);
            entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.PRESENCE_CONCEALMENT));
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.GENERIC_UNCAST.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        } else {
            tag.putBoolean("ShadeStep", true);
            entity.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.PRESENCE_CONCEALMENT), Integer.MAX_VALUE, 1, false, false, false));
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.PRESENCE_CONCEALMENT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }

        instance.addMasteryPoint(entity);
    }


}