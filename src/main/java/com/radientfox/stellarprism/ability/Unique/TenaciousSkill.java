package com.radientfox.stellarprism.ability.Unique;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.jetbrains.annotations.Nullable;

public class TenaciousSkill extends Skill {

    private static final String TENACIOUS_CD = "stellarprism.tenacious_cd";
    private static final int COOLDOWN_TICKS = 20;

    private static final ResourceLocation TENACIOUS = ResourceLocation.fromNamespaceAndPath("stellarprism", "tenacious");


    public TenaciousSkill() {
        super(SkillType.UNIQUE);
    }

    @Override
    public @Nullable ResourceLocation getSkillIcon() {
        return ResourceLocation.fromNamespaceAndPath("stellarprism", "textures/skill/unique/tenacious.png");
    }

    @Override
    public MutableComponent getSkillDescription() {
        return Component.literal("<typewriter>Defined by your ability to push forward despite what blocks your path, growing stronger the harder the odds stack against you.");
    }

    @Override
    public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
        return true;
    }

    @Override
    public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
        AttributeInstance magiculeGain = entity.getAttribute(TensuraAttributes.MAGICULE_GAIN);

        if (magiculeGain != null && !magiculeGain.hasModifier(TENACIOUS)) {
            magiculeGain.addOrReplacePermanentModifier(new AttributeModifier(TENACIOUS, 2.0, Operation.ADD_VALUE));
        }
    }

    public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
        if (!(instance.getMastery() < (double) 0.0F) && !instance.isTemporarySkill()) {
            TensuraSkillInstance eye = new TensuraSkillInstance(ExtraSkills.ULTRASPEED_REGENERATION.get());
            eye.getOrCreateTag().putBoolean("NoMagiculeCost", true);
            SkillHelper.learnSkill(entity, eye);
        }
    }

    @Override
    public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
        AttributeInstance magiculeGain = entity.getAttribute(TensuraAttributes.MAGICULE_GAIN);

        if (magiculeGain != null) {
            magiculeGain.removeModifier(TENACIOUS);
        }
    }

    @Override
    public int getModes(ManasSkillInstance instance) {
        return 1;
    }

    @Override
    public String getModeId(ManasSkillInstance instance, int mode) {
        return switch (mode) {
            case 0 -> "tenacious.revive";
            default -> super.getModeId(instance, mode);
        };
    }

    @Override
    public boolean onDeath(ManasSkillInstance instance, LivingEntity entity, DamageSource source) {
        if (entity.isAlive()) {
            return true;
        }

        long gameTime = entity.level().getGameTime();
        CompoundTag tag = instance.getOrCreateTag();

        long nextUse = tag.getLong("TenaciousCooldown");
        if (gameTime < nextUse) {
            return true;
        }

        tag.putLong("TenaciousCooldown", gameTime + 20 * 30);
        instance.markDirty();
        entity.invulnerableTime = 60;
        entity.setHealth(Math.max(entity.getMaxHealth() * 0.10F, 1.0F));
        entity.removeAllEffects();
        entity.clearFire();
        instance.setCoolDown(30, 0);
        instance.addMasteryPoint(entity);

        if (entity.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.HEART, entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ(), 12, 0.5, 0.5, 0.5, 0.05);
        }
        return false;
    }
}
