package com.radientfox.stellarprism.ability.Unique;

import com.radientfox.stellarprism.Registry.main.StellarItems;
import io.github.manasmods.manascore.attribute.api.ManasCoreAttributes;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.battlewill.Battlewill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.data.TensuraBlockTags;
import io.github.manasmods.tensura.enchantment.TensuraEnchantmentHelper;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.world.TensuraGameRules;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ChosenKingSkill extends Skill {
    private static final ResourceLocation CHOSEN_KING = ResourceLocation.fromNamespaceAndPath("stellarprism", "chosen_king");

    public ChosenKingSkill() {
        super(SkillType.UNIQUE);
    }

    @Override
    public MutableComponent getSkillDescription() {
        return Component.literal("The one destined to become a king through his own resolve and mastery of battle.");
    }

    @Override
    public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
        return true;
    }

    public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 120, 0, false, false, false));
        AttributeInstance melee = entity.getAttribute(TensuraAttributes.AUTO_MELEE_DODGE_CHANCE);
        if (melee != null) {
            melee.addOrReplacePermanentModifier(new AttributeModifier(CHOSEN_KING, 30.0D, AttributeModifier.Operation.ADD_VALUE));
        }

        AttributeInstance projectile = entity.getAttribute(TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE);
        if (projectile != null) {
            projectile.addOrReplacePermanentModifier(new AttributeModifier(CHOSEN_KING, 100.0D, AttributeModifier.Operation.ADD_VALUE));
        }
    }

    public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
        entity.removeEffect(MobEffects.REGENERATION);
        AttributeInstance melee = entity.getAttribute(TensuraAttributes.AUTO_MELEE_DODGE_CHANCE);
        if (melee != null) {
            melee.removeModifier(CHOSEN_KING);
        }

        AttributeInstance projectile = entity.getAttribute(TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE);
        if (projectile != null) {
            projectile.removeModifier(CHOSEN_KING);
        }
    }

    private boolean canSlash(ItemStack stack) {
        if (stack.is(ItemTags.SWORDS)) {
            return true;
        } else {
            return stack.is(ItemTags.SWORD_ENCHANTABLE) ? true : stack.is(ItemTags.AXES);
        }
    }

    @Override
    public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
        if (!this.isInSlot(owner, instance) || !instance.isToggled()) {
            return true;
        }

        int masteredBattlewills = 0;

        for (ManasSkillInstance skill : SkillAPI.getSkillsFrom(owner).getLearnedSkills()) {
            if (skill.getSkill() instanceof Battlewill && skill.isMastered(owner)) {
                masteredBattlewills++;
            }
        }

        float bonusMultiplier = 1.5F + (masteredBattlewills * 0.01F);

        if (TensuraDamageHelper.isPhysicalOrBattlewill(source, owner)) {
            amount.set((Float) amount.get() * bonusMultiplier);
        }

        return true;
    }

    public void onTick(ManasSkillInstance instance, LivingEntity entity) {
        if (!instance.isToggled()) return;
        if (entity.level().isClientSide) return;
        if (!(entity instanceof Player player)) return;
        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();

        if (mainHand.getItem() instanceof SwordItem) {
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 120, 1, false, false, false));
            entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 120, 0, false, false, false));

            if (offHand.getItem() instanceof ShieldItem) {
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 120, 0, false, false, false));
            }
            if (mainHand.getItem() instanceof ShieldItem) {
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 120, 1, false, false, false));
            }
            if (mainHand.getItem() instanceof PickaxeItem) {
                entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 120, 1, false, false, false));
            }
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
            case 0 -> "chosen_king.aura";
            case 1 -> "chosen_king.sslash";
            case 2 -> "chosen_king.aslash";
            default -> super.getModeId(instance, mode);
        };
    }

    @Override
    public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
        if (entity.level().isClientSide) return;
        if (!(entity instanceof Player player)) return;
        ItemStack mainHand = player.getMainHandItem();

        if (mode == 0) {
            for (ItemStack stack : player.getInventory().items) {
                if (mainHand.getItem() instanceof SwordItem) {
                    if (!stack.isEmpty() && stack.is(StellarItems.EXCALIBUR.get())) {
                        entity.addEffect(new MobEffectInstance(TensuraMobEffects.AURA_SWORD, 120, 1, false, false, false));


                    }
                } else {
                    entity.addEffect(new MobEffectInstance(TensuraMobEffects.AURA_SWORD, 120, 0, false, false, false));

                }
            }
        }
        if (mode == 1) {
            if (entity.onGround() || entity.isInWaterOrBubble()) {
                if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
                    instance.addMasteryPoint(entity);
                    ServerLevel level = (ServerLevel) entity.level();
                    double range = instance.isMastered(entity) ? 20.0 : 15.0;
                    BlockHitResult result = ObjectSelectionHelper.getPlayerPOVHitResult(level, entity, ClipContext.Fluid.NONE, range);
                    BlockPos resultPos = result.getBlockPos().relative(result.getDirection());
                    Vec3 vec3 = ObjectSelectionHelper.getFloorPos(resultPos);
                    if (!level.getBlockState(resultPos).canBeReplaced()) {
                        vec3 = ObjectSelectionHelper.getFloorPos(resultPos.above());
                    }

                    if (level.getBlockState(resultPos).is(TensuraBlockTags.SKILL_NOT_TELEPORTABLE)) {
                        level.playSound((Player) null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent) TensuraSoundEvents.GENERIC_CAST_FAIL.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    } else if (!entity.level().getWorldBorder().isWithinBounds(ObjectSelectionHelper.getBlockPos(vec3))) {
                        entity.sendSystemMessage(Component.translatable("tensura.skill.teleport.out_border").withStyle(ChatFormatting.RED));
                    } else {
                        Vec3 source = entity.position().add((double) 0.0F, (double) (entity.getBbHeight() / 2.0F), (double) 0.0F);
                        Vec3 offSetToTarget = vec3.subtract(source);

                        for (int particleIndex = 1; particleIndex < Mth.floor(offSetToTarget.length()); ++particleIndex) {
                            Vec3 particlePos = source.add(offSetToTarget.normalize().scale((double) particleIndex));
                            level.sendParticles(ParticleTypes.CLOUD, particlePos.x, particlePos.y, particlePos.z, 1, (double) 0.0F, (double) 0.0F, (double) 0.0F, (double) 0.0F);
                            TensuraParticleHelper.addServerParticlesAroundPos(entity.getRandom(), level, particlePos, ParticleTypes.SWEEP_ATTACK, (double) 3.0F);
                            TensuraParticleHelper.addServerParticlesAroundPos(entity.getRandom(), level, particlePos, ParticleTypes.SWEEP_ATTACK, (double) 2.0F);
                            AABB aabb = (new AABB(ObjectSelectionHelper.getBlockPos(particlePos))).inflate(Math.max(entity.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE), (double) 2.0F));
                            List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, aabb, (targetx) -> !targetx.is(entity) && !targetx.isAlliedTo(entity));
                            if (!list.isEmpty()) {
                                float bonus = instance.isMastered(entity) ? 75.0F : 15.0F;
                                float amount = (float) (entity.getAttributeValue(Attributes.ATTACK_DAMAGE) * entity.getAttributeValue(ManasCoreAttributes.CRITICAL_DAMAGE_MULTIPLIER));

                                for (LivingEntity target : list) {
                                    if (target.invulnerableTime < 40) {
                                        DamageSource damageSource = this.createSource(instance, entity, DamageTypes.MOB_ATTACK, mode);
                                        if (target.hurt(damageSource, amount + bonus)) {
                                            ItemStack stack = entity.getMainHandItem();
                                            stack.getItem().hurtEnemy(stack, target, entity);
                                            EnchantmentHelper.doPostAttackEffectsWithItemSource(level, target, damageSource, stack);
                                            TensuraEnchantmentHelper.doAdditionalAfterDamage(level, target, entity, damageSource, stack, amount + bonus);
                                            entity.level().playSound((Player) null, target.getX(), target.getY(), target.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, entity.getSoundSource(), 1.0F, 1.0F);
                                            if (level instanceof ServerLevel) {
                                                level.getChunkSource().broadcastAndSend(entity, new ClientboundAnimatePacket(entity, 4));
                                            }
                                        }

                                        TensuraEnchantmentHelper.doAdditionalAfterAttack(level, target, entity, damageSource, entity.getMainHandItem(), amount + bonus);
                                        target.invulnerableTime = 40;
                                    }
                                }
                            }
                        }

                        entity.resetFallDistance();
                        entity.unRide();
                        entity.teleportTo(vec3.x(), vec3.y(), vec3.z());
                        entity.swing(InteractionHand.MAIN_HAND, true);
                        level.playSound((Player) null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent) TensuraSoundEvents.INSTANT_MOVE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    }
                }
            }

        }
        if (mode == 2) {
            if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
                LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, instance.isMastered(entity) ? 6 * (double) 2.0F : 10, false);
                Level level = entity.level();
                entity.swing(InteractionHand.MAIN_HAND, true);
                double attack = entity.getAttributeValue(Attributes.ATTACK_DAMAGE);
                if (target != null) {
                    DamageSource source = this.createSource(instance, entity, DamageTypes.PLAYER_ATTACK, mode);
                    if (target.hurt(source, (float) (attack * 1.5F))) {
                        SkillHelper.knockBack(entity, target, 2.0F);
                        instance.addMasteryPoint(entity);
                        level.playSound((Player) null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.NEUTRAL, 1.0F, 1.0F);
                    }
                } else {
                    instance.addMasteryPoint(entity);
                    this.slash(entity, level, 3.0F, instance, mode);
                }
            }
        }
    }

    private void slash(LivingEntity entity, Level level, float distance, ManasSkillInstance instance, int mode) {
        Vec3 target = entity.position().add(entity.getLookAngle().scale((double) distance));
        Vec3 source = entity.position().add((double) 0.0F, (double) entity.getEyeHeight(), (double) 0.0F);
        Vec3 sourceToTarget = target.subtract(source);
        Vec3 normalizes = sourceToTarget.normalize();
        level.playSound((Player) null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.WARDEN_ATTACK_IMPACT, SoundSource.PLAYERS, 1.0F, 1.0F);

        for (int particleIndex = 1; particleIndex < Mth.floor(sourceToTarget.length()); ++particleIndex) {
            Vec3 particlePos = source.add(normalizes.scale((double) particleIndex));
            TensuraParticleHelper.spawnServerParticles(level, TensuraParticleUtils.getColorlessWave(0.9F, 2.0F), particlePos.x, particlePos.y, particlePos.z);
            if (TensuraGameRules.canSkillGrief(level)) {
                SkillHelper.launchBlock(entity, particlePos, 2, 1, 0.3F, 0.2F, (blockState) -> entity.getRandom().nextInt(2) != 1 ? false : blockState.is(TensuraBlockTags.EARTH_SKILL_BREAKABLE), (pos) -> !pos.equals(entity.getOnPos()) && !pos.equals(entity.getOnPos().below()), instance);
            }

            AABB aabb = (new AABB(ObjectSelectionHelper.getBlockPos(particlePos))).inflate((double) 2.0F);
            List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, aabb, (entityData) -> !entityData.is(entity));
            if (!list.isEmpty()) {
                for (LivingEntity living : list) {
                    DamageSource damageSource = this.createSource(instance, entity, DamageTypes.PLAYER_ATTACK, mode);
                    if (living.hurt(damageSource, (float) (entity.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5))) {
                        TensuraParticleHelper.spawnServerGroundSlamParticle(living, 10, 2.0F);
                        living.getDeltaMovement().add((double) 0.0F, 0.3, (double) 0.0F);
                    }
                }
            }
        }

    }
}




