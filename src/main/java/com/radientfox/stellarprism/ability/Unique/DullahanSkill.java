package com.radientfox.stellarprism.ability.Unique;

import com.radientfox.stellarprism.config.skills.StellarUniqueConfig;
import com.radientfox.stellarprism.util.StellarUtilsM;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DullahanSkill extends Skill {
    private static final StellarUniqueConfig.Dullahan CONFIG = ConfigRegistry.getConfig(StellarUniqueConfig.class).Dullahan;


    private static final ResourceLocation DULLAHAN = ResourceLocation.fromNamespaceAndPath("mythos", "dullahan");

    public DullahanSkill() {
        super(SkillType.UNIQUE);

    }

    public @Nullable ResourceLocation getSkillIcon() {
        return ResourceLocation.fromNamespaceAndPath("stellarprism", "textures/skill/unique/dullahan.png");
    }

    @Override
    public MutableComponent getSkillDescription() {
        return Component.literal("Dark, Angry, sullen...such a head no mortal ever saw before...");
    }

    @Override
    public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
        return CONFIG.mpAcquirement;
    }

    @Override
    public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
        return true;
    }

    @Override
    public void onTick(ManasSkillInstance instance, LivingEntity entity) {
        if (!(entity instanceof Player player)) return;
        if (player.level().isClientSide) return;

        CompoundTag tag = instance.getOrCreateTag();

        if (tag.getBoolean("SoundlessCoach")) {
            entity.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.PRESENCE_CONCEALMENT), 220, 2, false, false, false));
        }

        if (tag.getBoolean("SpineWhipActive")) {

            if (!EnergyHelper.isOutOfEnergy(entity, instance, 1)) {

                applySpineWhipEffects(instance, player, (ServerLevel) player.level());

                spawnSpineWhipParticles((ServerLevel) player.level(), player);

            } else {

                tag.putBoolean("SpineWhipActive", false);
            }
        }
    }

    public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
        if (!(instance.getMastery() < (double) 0.0F) && !instance.isTemporarySkill()) {
            TensuraSkillInstance eye = new TensuraSkillInstance(ExtraSkills.UNIVERSAL_PERCEPTION.get());
            eye.getOrCreateTag().putBoolean("NoMagiculeCost", true);
            SkillHelper.learnSkill(entity, eye);
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
            case 0 -> "dullahan.iris_out";
            case 1 -> "dullahan.spine_whip";
            case 2 -> "dullahan.soundless_coach";
            case 3 -> "dullahan.god_of_sacrifice";
            default -> super.getModeId(instance, mode);
        };
    }

    @Override
    public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
        return switch (mode) {
            case 0 -> CONFIG.irisOutCost;
            case 1 -> CONFIG.spineWhipCost;
            case 2 -> CONFIG.soundlessCoachCost;
            case 3 -> CONFIG.godOfSacrificeCost;
            default -> 0.0D;
        };
    }

    @Override
    public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {

        switch (mode) {

            case 0:
                irisOutMode(instance, entity, entity.level());
                instance.setCoolDown(CONFIG.irisOutCooldown, 0);
                break;

            case 1:
                spineWhipMode(instance, entity, entity.level());
                instance.setCoolDown(CONFIG.spineWhipCooldown, 1);
                break;

            case 2:
                soundlessCoachMode(instance, entity, entity.level());
                instance.setCoolDown(CONFIG.soundlessCoachCooldown, 2);
                break;

            case 3:
                godOfSacrificeMode(instance, entity, entity.level());
                instance.setCoolDown(CONFIG.godOfSacrificeCooldown, 3);
                break;
        }
    }

    private void irisOutMode(ManasSkillInstance instance, LivingEntity entity, Level level) {
        if (!(entity instanceof Player player)) return;
        if (EnergyHelper.isOutOfEnergy(entity, instance, 0)) return;
        if (level.isClientSide) return;

        ServerLevel serverLevel = (ServerLevel) level;

        double damage = instance.isMastered(entity) ? 340.0 : 180.0;

        Entity target = StellarUtilsM.getTargetEntity(player, 15);

        if (target instanceof LivingEntity livingTarget) {

            float hpPercent = livingTarget.getHealth() / livingTarget.getMaxHealth();

            if (hpPercent <= 0.20F) {

                ItemStack soulLantern = new ItemStack(Items.SOUL_LANTERN);

                soulLantern.set(
                        DataComponents.CUSTOM_NAME,
                        Component.literal("Trapped Soul Lantern")
                                .withStyle(ChatFormatting.AQUA)
                );

                soulLantern.set(
                        DataComponents.MAX_STACK_SIZE,
                        64
                );

                livingTarget.spawnAtLocation(soulLantern);

                serverLevel.sendParticles(
                        ParticleTypes.SOUL_FIRE_FLAME,
                        livingTarget.getX(),
                        livingTarget.getY() + 1.0D,
                        livingTarget.getZ(),
                        50,
                        0.5D,
                        0.8D,
                        0.5D,
                        0.02D
                );

                serverLevel.sendParticles(
                        ParticleTypes.SMOKE,
                        livingTarget.getX(),
                        livingTarget.getY() + 1.0D,
                        livingTarget.getZ(),
                        20,
                        0.4D,
                        0.6D,
                        0.4D,
                        0.01D
                );

                serverLevel.playSound(
                        null,
                        livingTarget.blockPosition(),
                        SoundEvents.SOUL_ESCAPE.value(),
                        SoundSource.PLAYERS,
                        1.0F,
                        0.7F
                );

                livingTarget.discard();

                player.swing(InteractionHand.MAIN_HAND, true);
                instance.addMasteryPoint(player);

                return;
            }

            livingTarget.hurt(
                    serverLevel.damageSources().playerAttack(player),
                    (float) damage
            );

            livingTarget.addEffect(
                    new MobEffectInstance(
                            TensuraMobEffects.getReference(TensuraMobEffects.FEAR),
                            200,
                            7,
                            false,
                            true,
                            false
                    )
            );

            livingTarget.addEffect(
                    new MobEffectInstance(
                            TensuraMobEffects.getReference(TensuraMobEffects.TRUE_BLINDNESS),
                            200,
                            7,
                            false,
                            true,
                            false
                    )
            );

            serverLevel.sendParticles(
                    ParticleTypes.SOUL_FIRE_FLAME,
                    livingTarget.getX(),
                    livingTarget.getY() + 1.0D,
                    livingTarget.getZ(),
                    40,
                    0.5D,
                    0.8D,
                    0.5D,
                    0.02D
            );

            if (!livingTarget.isAlive()) {

                ItemStack soulLantern = new ItemStack(Items.SOUL_LANTERN);

                soulLantern.set(
                        DataComponents.CUSTOM_NAME,
                        Component.literal("Trapped Soul Lantern")
                                .withStyle(ChatFormatting.AQUA)
                );

                soulLantern.set(
                        DataComponents.MAX_STACK_SIZE,
                        64
                );

                livingTarget.spawnAtLocation(soulLantern);

                serverLevel.playSound(
                        null,
                        livingTarget.blockPosition(),
                        SoundEvents.SOUL_ESCAPE.value(),
                        SoundSource.PLAYERS,
                        1.0F,
                        0.7F
                );

                instance.addMasteryPoint(player);
            }
        }

        serverLevel.playSound(
                null,
                player.blockPosition(),
                SoundEvents.PLAYER_ATTACK_SWEEP,
                SoundSource.PLAYERS,
                1.0F,
                0.8F
        );

        player.swing(InteractionHand.MAIN_HAND, true);
        instance.addMasteryPoint(entity);
    }

    private void spineWhipMode(ManasSkillInstance instance, LivingEntity entity, Level level) {
        if (!(entity instanceof Player player)) return;
        if (level.isClientSide) return;

        CompoundTag tag = instance.getOrCreateTag();
        boolean isActive = tag.getBoolean("SpineWhipActive");

        tag.putBoolean("SpineWhipActive", !isActive);
    }

    private void applySpineWhipEffects(ManasSkillInstance instance, Player player, ServerLevel serverLevel) {

        List<LivingEntity> targets = serverLevel.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(15.0), target -> !target.isAlliedTo(player) && target.isAlive() && target != player);

        for (LivingEntity target : targets) {

            if (target instanceof Player p && p.getAbilities().instabuild) continue;

            target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.TRUE_BLINDNESS), 100, 1, false, false, false));

            target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.HYPNOSIS), 200, 2, false, false, false));

            target.invulnerableTime = 0;

            target.hurt(serverLevel.damageSources().playerAttack(player), 50.0F);

            if (!target.isAlive()) {
                ItemStack soulLantern = new ItemStack(Items.SOUL_LANTERN);
                soulLantern.set(DataComponents.CUSTOM_NAME, Component.literal("Trapped Soul Lantern").withStyle(ChatFormatting.AQUA));
                soulLantern.set(DataComponents.MAX_STACK_SIZE, 64);
                target.spawnAtLocation(soulLantern);
                instance.addMasteryPoint(player);
                player.giveExperiencePoints(25);
                serverLevel.playSound(null, target.blockPosition(), SoundEvents.SOUL_ESCAPE.value(), SoundSource.PLAYERS, 1.0F, 0.7F);
            }
        }
    }

    private void soundlessCoachMode(ManasSkillInstance instance, LivingEntity entity, Level level) {
        if (!(entity instanceof Player player)) return;
        if (level.isClientSide) return;

        CompoundTag tag = instance.getOrCreateTag();
        boolean isConcealed = tag.getBoolean("SoundlessCoach");

        if (isConcealed) {
            tag.putBoolean("SoundlessCoach", false);
            entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.PRESENCE_CONCEALMENT));
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.GENERIC_UNCAST.get(), SoundSource.PLAYERS, 1.0F, 1.0F);

            player.displayClientMessage(Component.literal("Soundless Coach deactivated.").withStyle(ChatFormatting.GRAY), true);
        } else {
            tag.putBoolean("SoundlessCoach", true);
            entity.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.PRESENCE_CONCEALMENT), Integer.MAX_VALUE, 2, false, false, false));
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.PRESENCE_CONCEALMENT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);

            player.displayClientMessage(Component.literal("Soundless Coach activated.").withStyle(ChatFormatting.DARK_GRAY), true);
        }

        instance.addMasteryPoint(entity);
    }

    private void godOfSacrificeMode(ManasSkillInstance instance, LivingEntity entity, Level level) {
        if (!(entity instanceof Player player)) return;
        if (level.isClientSide) return;
        if (!instance.isMastered(player)) {
            player.displayClientMessage(Component.literal("The harvest gods have not yet chosen you.").withStyle(ChatFormatting.BLACK), true);
            return;
        }

        ItemStack mainHand = player.getMainHandItem();

        if (!mainHand.is(Items.SOUL_LANTERN)) {
            player.displayClientMessage(Component.literal("You must hold a Trapped Soul Lantern.").withStyle(ChatFormatting.RED), true);
            return;
        }

        Component customName = mainHand.get(DataComponents.CUSTOM_NAME);

        if (customName == null || !customName.getString().equals("Trapped Soul Lantern")) {

            player.displayClientMessage(Component.literal("This lantern contains no trapped soul.").withStyle(ChatFormatting.RED), true);
            return;
        }

        int count = mainHand.getCount();
        int hpGain = 30 * count;
        int shpGain = 20 * count;
        mainHand.shrink(count);

        AttributeInstance maxHealth = player.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH);
        if (maxHealth != null) {
            double currentBonus = 0;
            AttributeModifier existing = maxHealth.getModifier(DULLAHAN);
            if (existing != null) {
                currentBonus = existing.amount();
            }
            maxHealth.addOrReplacePermanentModifier(new AttributeModifier(DULLAHAN, currentBonus + hpGain, AttributeModifier.Operation.ADD_VALUE));
        }

        AttributeInstance spiritualHealth = player.getAttribute(TensuraAttributes.MAX_SPIRITUAL_HEALTH);
        if (spiritualHealth != null) {
            double currentBonus = 0;
            AttributeModifier existing = spiritualHealth.getModifier(DULLAHAN);
            if (existing != null) {
                currentBonus = existing.amount();
            }
            spiritualHealth.addOrReplacePermanentModifier(new AttributeModifier(DULLAHAN, currentBonus + shpGain, AttributeModifier.Operation.ADD_VALUE));
        }
        level.playSound(null, player.blockPosition(), SoundEvents.SOUL_ESCAPE.value(), SoundSource.PLAYERS, 1.0F, 0.8F);
        player.swing(InteractionHand.MAIN_HAND, true);
        instance.addMasteryPoint(entity);
    }

    private void spawnSpineWhipParticles(ServerLevel level, Player player) {

        double radius = 1.8D;
        int points = 80;
        for (int i = 0; i < points; i++) {
            double angle = (Math.PI * 2D * i) / points;
            double x = player.getX() + Math.cos(angle) * radius;
            double y = player.getY() + 1.0D;
            double z = player.getZ() + Math.sin(angle) * radius;
            level.sendParticles(ParticleTypes.SMOKE, x, y, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
        return instance.isMastered(living);
    }
}