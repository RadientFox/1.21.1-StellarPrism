package com.radientfox.stellarprism.ability.Unique;

import com.radientfox.stellarprism.config.skills.StellarUniqueConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.extra.ThoughtAccelerationSkill;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.Alignment;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class EltnamSkill extends Skill {

    private static final StellarUniqueConfig.Eltnam CONFIG = ConfigRegistry.getConfig(StellarUniqueConfig.class).Eltnam;
    private static final ResourceLocation ELTNAM = ResourceLocation.fromNamespaceAndPath("stellarprism", "eltnam");

    public EltnamSkill() {
        super(Skill.SkillType.UNIQUE);
    }

    public @Nullable ResourceLocation getSkillIcon() {
        return ResourceLocation.fromNamespaceAndPath("stellarprism", "textures/skill/unique/eltnam.png");
    }

    @Override
    public MutableComponent getSkillDescription() {
        return Component.literal("<typewriter><shadow r=1 a=0.6>§5Cut! There are no second chances for actors that fall to the abyss...");
    }

    @Override
    public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
        return true;
    }

    public double getDefaultAcquiringMagiculeCost() {
        return CONFIG.mpAcquirement;
    }

    public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
        return true;
    }

    @Override
    public void onTick(ManasSkillInstance instance, LivingEntity entity) {
        TensuraStorages.getExistenceFrom(entity).setAlignment(Alignment.CHAOS);
        TensuraStorages.getExistenceFrom(entity).markDirty();
        entity.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.INSTANT_REGENERATION), 240, 0, false, false, false));

    }


    @Override
    public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
        AttributeHelper.multiplyChantSpeed(entity, 2.0);
        ThoughtAccelerationSkill.onToggle(instance, entity, ELTNAM, true);

        AttributeInstance learning = entity.getAttribute(TensuraAttributes.ABILITY_LEARNING_GAIN);
        if (learning != null) {
            learning.addOrReplacePermanentModifier(
                    new AttributeModifier(ELTNAM, 6.0, AttributeModifier.Operation.ADD_VALUE)
            );
        }

        AttributeInstance mastery = entity.getAttribute(TensuraAttributes.ABILITY_MASTERY_GAIN);
        if (mastery != null) {
            mastery.addOrReplacePermanentModifier(
                    new AttributeModifier(ELTNAM, 6.0, AttributeModifier.Operation.ADD_VALUE)
            );
        }
    }

    @Override
    public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
        AttributeHelper.removeChantSpeed(entity, 2.0);
        ThoughtAccelerationSkill.onToggle(instance, entity, ELTNAM, false);

        AttributeInstance learning = entity.getAttribute(TensuraAttributes.ABILITY_LEARNING_GAIN);
        if (learning != null) {
            learning.removeModifier(ELTNAM);
        }

        AttributeInstance mastery = entity.getAttribute(TensuraAttributes.ABILITY_MASTERY_GAIN);
        if (mastery != null) {
            mastery.removeModifier(ELTNAM);
        }
        MobEffectInstance regeneration = entity.getEffect(TensuraMobEffects.getReference(TensuraMobEffects.INSTANT_REGENERATION));
        if (regeneration != null && regeneration.getAmplifier() < 1) {
            entity.removeEffect(regeneration.getEffect());
        }
    }

    public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
        super.onLearnSkill(instance, entity);
        if (!(instance.getMastery() < (double) 0.0F) && !instance.isTemporarySkill()) {
            TensuraSkillInstance eye = new TensuraSkillInstance(ExtraSkills.CHANT_ANNULMENT.get());
            eye.getOrCreateTag().putBoolean("NoMagiculeCost", true);
            SkillHelper.learnSkill(entity, eye);
        }
    }

    @Override
    public int getModes(ManasSkillInstance instance) {
        return 3;
    }

    @Override
    public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
        if (reverse) return (mode == 0) ? 2 : (mode - 1);

        return (mode == 2) ? 0 : (mode + 1);
    }

    @Override
    public String getModeId(ManasSkillInstance instance, int mode) {
        return switch (mode) {
            case 0 -> "eltnam.scry_proficiency: analysis";
            case 1 -> "eltnam.scry_proficiency: divination";
            case 2 -> "eltnam.synthetic_blood_formula";
            default -> super.getModeId(instance, mode);
        };
    }

    @Override
    public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
        return switch (mode) {
            case 0 -> CONFIG.scryProficiencyCost;
            case 1 -> CONFIG.divinationCost;
            case 2 -> CONFIG.syntheticBloodFormulaCost;
            default -> 0.0D;
        };
    }

    @Override
    public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {

        if (entity.level().isClientSide()) return;

        if (!(entity instanceof ServerPlayer player)) return;

        instance.getOrCreateTag().putInt("CurrentMode", mode);

        switch (mode) {

            case 0 -> {

                CompoundTag data = instance.getOrCreateTag();

                if (player.isShiftKeyDown()) {
                    int analysisMode = data.getInt("AnalysisMode");

                    analysisMode = switch (analysisMode) {
                        case 1 -> 2;
                        case 2 -> 3;
                        default -> 1;
                    };

                    data.putInt("AnalysisMode", analysisMode);

                    player.displayClientMessage(Component.literal("Analysis Mode: " + analysisMode), true);

                } else {

                    boolean active = data.getBoolean("AnalysisActive");

                    if (active) {
                        AttributeHelper.removeAnalysisAttributes(player, true, true, false);
                        data.putBoolean("AnalysisActive", false);

                    } else {

                        int level = isMastered(instance, entity) ? 30 : 20;

                        AttributeHelper.addAnalysisAttributes(player, level, 30);

                        data.putBoolean("AnalysisActive", true);
                    }
                }

            }

            case 1 -> {

                if (!instance.isMastered(player)) {
                    player.displayClientMessage(
                            Component.literal("<shadow r=1 a=0.6>You must master Eltnam to Divinate a soul...")
                                    .withStyle(ChatFormatting.RED),
                            true
                    );
                    break;
                }

                ItemStack held = player.getMainHandItem();

                String name = held.getHoverName().getString().trim();

                ServerPlayer target = player.getServer().getPlayerList().getPlayers().stream()
                        .filter(p -> p.getName().getString().equalsIgnoreCase(name))
                        .findFirst()
                        .orElse(null);

                if (target == null) {
                    player.displayClientMessage(
                            Component.literal("Player '" + name + "' could not be found.")
                                    .withStyle(ChatFormatting.RED),
                            true
                    );
                    break;
                }

                String dimension = target.serverLevel()
                        .dimension()
                        .location()
                        .toString();

                player.displayClientMessage(
                        Component.literal(
                                "§5" + target.getName().getString() + "'s Location: "
                                        + "§bX=" + String.format("%.1f", target.getX())
                                        + " §bY=" + String.format("%.1f", target.getY())
                                        + " §bZ=" + String.format("%.1f", target.getZ())
                                        + " §5| Dimension: §d" + dimension
                        ),
                        true
                );

                TensuraParticleHelper.addServerParticlesAroundSelf(
                        player,
                        ParticleTypes.PORTAL,
                        1.0D
                );
                instance.setCoolDown(60, 1);
            }

            case 2 -> {
                LivingEntity target = entity.isShiftKeyDown() ? ObjectSelectionHelper.getTargetingEntity(entity, 5.0F, false) : null;
                entity.swing(InteractionHand.MAIN_HAND, true);
                if (target != null) {
                    instance.addMasteryPoint(entity);
                    instance.setCoolDown(instance.isMastered(entity) ? 10 : 5, mode);
                    double cost = instance.isMastered(entity) ? 60.0F : 30.0F;
                    float healingHP = target.getMaxHealth() - target.getHealth();
                    double lackedMana = EnergyHelper.isOutOfMagiculeConsuming(entity, (int) ((double) healingHP * cost));
                    if (lackedMana > (double) 0.0F) {
                        healingHP = (float) ((double) healingHP - lackedMana / cost);
                    }

                    target.heal(healingHP);
                    if (instance.isMastered(entity)) {
                        IExistence existence = TensuraStorages.getExistenceFrom(target);
                        double healingSpiritual = existence.getSpiritualHealth();
                        double lackedSpiritual = target.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH) - healingSpiritual;
                        double lackedMP = EnergyHelper.isOutOfMagiculeConsuming(entity, (int) (lackedSpiritual * 50.0F));
                        if (lackedMP > (double) 0.0F) {
                            lackedSpiritual -= lackedMP / 50.0F;
                        }

                        existence.setSpiritualHealth(healingSpiritual + lackedSpiritual);
                        existence.markDirty();
                    }

                    instance.addMasteryPoint(player);
                    TensuraParticleHelper.addServerParticlesAroundSelf(target, ParticleTypes.COMPOSTER, 1.0F);
                    entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.GENERIC_HEAL.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    TensuraParticleHelper.spawnServerParticles(entity.level(), TensuraParticleUtils.getGreenWave(0.9F, target.getBbWidth() * 3.0F, -0.5F, true), target.getX(), target.getY() + (double) target.getBbHeight() * 0.33, target.getZ());
                    TensuraParticleHelper.spawnServerParticles(entity.level(), TensuraParticleUtils.getGreenWave(0.9F, target.getBbWidth() * 3.0F, -0.5F, true), target.getX(), target.getY() + (double) target.getBbHeight() * 0.66, target.getZ());
                } else {
                    instance.setCoolDown(instance.isMastered(entity) ? 10 : 5, mode);
                    entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.INFECTION));
                    double cost = instance.isMastered(entity) ? 60.0F : 30.0F;
                    float healingHP = entity.getMaxHealth() - entity.getHealth();
                    double lackedMana = EnergyHelper.isOutOfMagiculeConsuming(entity, (int) ((double) healingHP * cost));
                    if (lackedMana > (double) 0.0F) {
                        healingHP = (float) ((double) healingHP - lackedMana / cost);
                    }

                    entity.heal(healingHP);
                    if (instance.isMastered(entity)) {
                        IExistence existence = TensuraStorages.getExistenceFrom(entity);
                        double healingSpiritual = existence.getSpiritualHealth();
                        double lackedSpiritual = entity.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH) - healingSpiritual;
                        double lackedMP = EnergyHelper.isOutOfMagiculeConsuming(entity, (int) (lackedSpiritual * 50.0F));
                        if (lackedMP > (double) 0.0F) {
                            lackedSpiritual -= lackedMP / 50.0F;
                        }

                        existence.setSpiritualHealth(healingSpiritual + lackedSpiritual);
                        existence.markDirty();
                    }
                    instance.addMasteryPoint(player);
                    TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.COMPOSTER, 1.0F);
                    entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.GENERIC_HEAL.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    TensuraParticleHelper.spawnServerParticles(entity.level(), TensuraParticleUtils.getGreenWave(0.9F, entity.getBbWidth() * 3.0F, -0.5F, true), entity.getX(), entity.getY() + (double) entity.getBbHeight() * 0.33, entity.getZ());
                    TensuraParticleHelper.spawnServerParticles(entity.level(), TensuraParticleUtils.getGreenWave(0.9F, entity.getBbWidth() * 3.0F, -0.5F, true), entity.getX(), entity.getY() + (double) entity.getBbHeight() * 0.66, entity.getZ());
                }
            }
        }
    }
}


