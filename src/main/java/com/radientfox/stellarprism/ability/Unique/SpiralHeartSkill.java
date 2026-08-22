package com.radientfox.stellarprism.ability.Unique;

import com.radientfox.stellarprism.Registry.main.StellarEffects;
import com.radientfox.stellarprism.ability.entity.beam.SpiralElectroBlastBeam;
import com.radientfox.stellarprism.ability.entity.projectile.SpiralHeartProjectile;
import com.radientfox.stellarprism.config.skills.StellarUniqueConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.unique.ThrowerSkill;
import io.github.manasmods.tensura.effect.template.ITransformation;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SpiralHeartSkill extends Skill implements ITransformation {
    private static final StellarUniqueConfig.SpiralHeart CONFIG = ConfigRegistry.getConfig(StellarUniqueConfig.class).SpiralHeart;

    private static final ResourceLocation SPIRAL_HEART = ResourceLocation.fromNamespaceAndPath("stellarprism", "spiral_heart");

    private static final double COST_REDUCTION = -0.50D;
    private static final double DAMAGE_MULT = 3.0D;
    private static final double MASTER_DAMAGE_MULT = 5.0D;

    public SpiralHeartSkill() {
        super(SkillType.UNIQUE);
    }

    @Override
    public @Nullable ResourceLocation getSkillIcon() {
        return ResourceLocation.fromNamespaceAndPath("stellarprism", "textures/skill/unique/spiralheart.png");
    }

    @Override
    public MutableComponent getSkillDescription() {
        return Component.literal("Your drill is the drill that will pierce the heavens!");
    }

    @Override
    public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
        return true;
    }

    public double getDefaultAcquiringMagiculeCost() {
        return CONFIG.mpAcquirement;
    }

    @Override
    public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity entity, int mode) {
        return canTick(instance, entity);
    }

    @Override
    public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
        super.onForgetSkill(instance, entity);

        entity.removeEffect(StellarEffects.TENGEN_TOPPA);
    }

    @Override
    public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
        MobEffectInstance effect = entity.getEffect((StellarEffects.TENGEN_TOPPA));
        return effect != null;
    }

    @Override
    public void onTick(ManasSkillInstance instance, LivingEntity entity) {
        CompoundTag tag = instance.getOrCreateTag();
        int time = tag.getInt("activatedTimes");

        if (time % BASE_CONFIG.Mastery.masteryActivateTime == 0) {
            instance.addMasteryPoint(entity);
        }

        tag.putInt("activatedTimes", time + 1);
    }

    @Override
    public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
        AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.RESISTANCE_DEGRADATION, SPIRAL_HEART, 1.0D, Operation.ADD_VALUE);
        AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.DODGE_NEGATE_CHANCE, SPIRAL_HEART, 100.0F, Operation.ADD_VALUE);
        AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.MAGIC_COST_MULTIPLIER, SPIRAL_HEART, COST_REDUCTION, Operation.ADD_VALUE);
        double damageBonus = instance.isMastered(entity) ? MASTER_DAMAGE_MULT : DAMAGE_MULT;
        AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.PHYSICAL_RESIST_DEGRADATION, SPIRAL_HEART, damageBonus, Operation.ADD_MULTIPLIED_BASE);
        AttributeHelper.addPermanentAttribute(entity, Attributes.ATTACK_DAMAGE, SPIRAL_HEART, damageBonus, Operation.ADD_MULTIPLIED_BASE);
    }

    @Override
    public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
        AttributeHelper.removeAttribute(entity, TensuraAttributes.RESISTANCE_DEGRADATION, SPIRAL_HEART);
        AttributeHelper.removeAttribute(entity, TensuraAttributes.DODGE_NEGATE_CHANCE, SPIRAL_HEART);
        AttributeHelper.removeAttribute(entity, TensuraAttributes.MAGIC_COST_MULTIPLIER, SPIRAL_HEART);
        AttributeHelper.removeAttribute(entity, TensuraAttributes.PHYSICAL_RESIST_DEGRADATION, SPIRAL_HEART);
        AttributeHelper.removeAttribute(entity, Attributes.ATTACK_DAMAGE, SPIRAL_HEART);
    }

    @Override
    public int getModes(ManasSkillInstance instance) {
        return 3;
    }

    @Override
    public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
        if (reverse) {
            return mode == 0 ? 2 : mode - 1;
        }

        return mode == 2 ? 0 : mode + 1;
    }

    @Override
    public String getModeId(ManasSkillInstance instance, int mode) {
        return switch (mode) {
            case 0 -> "spiral_heart.boomerang";
            case 1 -> "spiral_heart.giga_break";
            case 2 -> "spiral_heart.tengen_toppa";
            default -> super.getModeId(instance, mode);
        };
    }

    @Override
    public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
        return switch (mode) {
            case 0 -> CONFIG.spiralThrowCost;
            case 1 -> CONFIG.gigaBreakCost;
            case 2 -> CONFIG.tengenToppaCost;
            default -> 0.0D;
        };
    }

    @Override
    public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
        switch (mode) {
            case 0 -> spiralThrow(instance, entity);

            case 1 -> gigaBreak(instance, entity);

            case 2 -> tengenToppa(instance, entity);

        }
    }

    private void spiralThrow(ManasSkillInstance instance, LivingEntity entity) {
        if (EnergyHelper.isOutOfEnergy(entity, instance, 0)) {
            return;
        }
        Level level = entity.level();
        ItemStack mainHandStack = entity.getMainHandItem();
        Projectile projectile;

        if (mainHandStack.isEmpty()) {
            projectile = ThrowerSkill.getProjectile(level, entity, mainHandStack.copy(), instance);
        } else {
            SpiralHeartProjectile spiralProjectile = new SpiralHeartProjectile(level, entity, mainHandStack.copy(), true, instance.isMastered(entity) ? 5.0F : 3.0F);

            spiralProjectile.getSourceItem().setCount(1);
            spiralProjectile.setSkill(instance);

            if (instance.isMastered(entity)) {
                spiralProjectile.setLoyaltyLevel(1);
            }

            projectile = spiralProjectile;
        }
        float speed = instance.isToggled() ? 3.0F : 6.0F;
        Vec3 direction = entity.getViewVector(speed);
        projectile.shoot(direction.x(), direction.y(), direction.z(), 2.0F + (float) entity.getDeltaMovement().length() * 2.0F, 0.0F);
        level.addFreshEntity(projectile);
        entity.swing(entity.getUsedItemHand(), true);
        if (!entity.hasInfiniteMaterials() && !mainHandStack.isEmpty()) {
            mainHandStack.shrink(1);
        }

        instance.setCoolDown(CONFIG.spiralThrowCooldown, 0);
        instance.addMasteryPoint(entity);
    }

    private void gigaBreak(ManasSkillInstance instance, LivingEntity entity) {
        if (EnergyHelper.isOutOfEnergy(entity, instance, 1)) {
            return;
        }

        float damage = instance.isMastered(entity) ? 200.0F : 100.0F;

        SpiralElectroBlastBeam beam = new SpiralElectroBlastBeam(entity.level(), entity);

        beam.setOwner(entity);
        beam.setLife(21);
        beam.setDamage(damage);
        beam.setSize(1.0F);
        beam.setRange(30);
        beam.setExplosionRadius(3);
        beam.setPos(entity.getEyePosition());
        beam.updateAngle(entity);

        entity.level().addFreshEntity(beam);
        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), io.github.manasmods.tensura.registry.sound.TensuraSoundEvents.CAST_LIGHTNING.get(), net.minecraft.sounds.SoundSource.PLAYERS, 0.8F, 0.5F);

        instance.setCoolDown(CONFIG.gigaBreakCooldown, 1);
        instance.addMasteryPoint(entity);
    }

    private void tengenToppa(ManasSkillInstance instance, LivingEntity entity) {
        if (!(entity instanceof Player player)) return;
        if (!instance.isMastered(player)) {

            player.displayClientMessage(Component.literal("You do not have enough spiral energy yet").withStyle(ChatFormatting.LIGHT_PURPLE), true);
            return;
        }

        if (!entity.hasEffect((StellarEffects.TENGEN_TOPPA))) {

            if (EnergyHelper.isOutOfEnergy(entity, instance, 2)) return;
            entity.setHealth(entity.getMaxHealth());
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.TRANSFORM_BEAST.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            entity.addEffect(new MobEffectInstance((StellarEffects.TENGEN_TOPPA), instance.isMastered(entity) ? 8000 : 6000, 0, false, false, false));
            IExistence existence = TensuraStorages.getExistenceFrom(entity);
            existence.setAura(Math.max(existence.getAura(), EnergyHelper.getMaxAura(entity)));
            existence.setMagicule(Math.max(existence.getMagicule(), EnergyHelper.getMaxMagicule(entity)));
            existence.markDirty();
            TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.EXPLOSION_EMITTER);
            TensuraParticleHelper.spawnServerParticles(entity.level(), TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(), entity.getX(), entity.getY(), entity.getZ(), 55, 0.08, 0.08, 0.08, 0.5F, true);
            TensuraParticleHelper.spawnServerParticles(entity.level(), TensuraParticleTypes.YELLOW_LIGHTNING_SPARK.get(), entity.getX(), entity.getY(), entity.getZ(), 55, 0.08, 0.08, 0.08, 0.5F, true);
            TensuraParticleHelper.spawnServerParticles(entity.level(), TensuraParticleUtils.getGoldWave(1.0F, entity.getBbWidth() * 7.0F, -0.5F, false), entity.getX(), entity.getY() + (double) (entity.getBbHeight() / 2.0F), entity.getZ());


            instance.addMasteryPoint(entity);
            instance.setCoolDown(CONFIG.tengenToppaCooldown, 2);

        } else {

            entity.removeEffect(StellarEffects.TENGEN_TOPPA);
            entity.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.PETRIFICATION), 300, 1, false, false, false));
            instance.setCoolDown(CONFIG.tengenToppaCooldown, 2);

            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.BUFF_DEACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }
}