package com.radientfox.stellarprism.ability.Unique;

import com.radientfox.stellarprism.config.skills.StellarUniqueConfig;
import com.radientfox.stellarprism.util.StellarUtilsM;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.Alignment;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleTypes;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;

import java.awt.*;
import java.util.List;


public class NimueSkill extends Skill {
    private static final StellarUniqueConfig.Nimue CONFIG = ConfigRegistry.getConfig(StellarUniqueConfig.class).Nimue;

    public NimueSkill() {
        super(SkillType.UNIQUE);
    }

    public @Nullable ResourceLocation getSkillIcon() {
        return ResourceLocation.fromNamespaceAndPath("stellarprism", "textures/skill/unique/remember.png");
    }

    @Override
    public MutableComponent getSkillDescription() {
        return Component.literal("Fantasy Becomes Reality");
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

        if (tag.getBoolean("Concealing")) {
            entity.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.PRESENCE_CONCEALMENT), 220, 0, false, false, false));
        }

        if (player.isInWaterOrBubble()) {

            player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 220, 0, false, false, false));

            player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 220, 2, false, false, false));

            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 220, 1, false, false, false));
        }

        if (player.tickCount % 2 == 0) {
        }
    }


    public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
        if (!(instance.getMastery() < (double) 0.0F) && !instance.isTemporarySkill()) {
            TensuraSkillInstance eye = new TensuraSkillInstance(ResistanceSkills.WATER_ATTACK_NULLIFICATION.get());
            eye.getOrCreateTag().putBoolean("NoMagiculeCost", true);
            SkillHelper.learnSkill(entity, eye);
        }
    }

    public boolean onBeingDamaged(ManasSkillInstance instance, LivingEntity entity, DamageSource source, float amount) {
        CompoundTag tag = instance.getOrCreateTag();

        if (tag.getBoolean("Concealing")) {
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20, 2, false, false, false));
        }

        if (entity.isInWater()) {
            double dodgeChance = instance.isMastered(entity) ? 0.40 : 0.30;
            if (entity.getRandom().nextDouble() < dodgeChance) {
                if (entity.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.CLOUD, entity.getX(), entity.getY() + 1, entity.getZ(), 10, 0.2, 0.2, 0.2, 0.05);
                }
                return false;
            }
        } else return true;

        return true;
    }

    @Override
    public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
        float currentAmount = amount.get();

        if (owner.isInWater()) {
            currentAmount *= 1.5f;
        }

        amount.set(currentAmount);
        return true;
    }

    @Override
    public int getModes(ManasSkillInstance instance) {
        return 3;
    }

    public String getModeId(ManasSkillInstance instance, int mode) {
        String var10000;
        switch (mode) {
            case 0 -> var10000 = "nimue.purifying_water";
            case 1 -> var10000 = "nimue.lilypad_step";
            case 2 -> var10000 = "nimue.lake_fairy";
            default -> var10000 = super.getModeId(instance, mode);
        }
        return var10000;
    }

    public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
        if (reverse) {
            return mode == 0 ? 2 : mode - 1;
        } else {
            return mode == 2 ? 0 : mode + 1;
        }
    }


    @Override
    public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
        instance.getOrCreateTag().putInt("CurrentMode", mode);

        Level level = entity.level();
        if (mode == 0) {
            if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {

                if (!(entity instanceof Player player)) return;
                if (level.isClientSide) return;

                CompoundTag tag = instance.getOrCreateTag();
                int elementalPoints = tag.getInt("CreationElementalPoints");
                boolean isCrouching = player.isShiftKeyDown();

                if (isCrouching && elementalPoints >= 50) {
                    double radius = 15.0;

                    List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(radius), e -> e != player);

                    for (LivingEntity target : targets) {
                        purify(target, (ServerLevel) level);
                        spawnPurifyParticles((ServerLevel) level, target, 2);
                    }

                    elementalPoints -= 50;
                    tag.putInt("CreationElementalPoints", elementalPoints);
                    instance.setCoolDown((int) CONFIG.purifyingWaterCooldown, mode);
                    instance.addMasteryPoint(entity);

                    player.displayClientMessage(Component.literal("Cleansing all spirits...").withStyle(ChatFormatting.AQUA), true);

                } else {
                    Entity target = StellarUtilsM.getTargetEntity(player, 5);

                    if (target instanceof LivingEntity livingTarget) {
                        purify(livingTarget, (ServerLevel) level);

                        player.displayClientMessage(Component.literal("Purifying target...").withStyle(ChatFormatting.BLUE), true);

                        spawnPurifyParticles((ServerLevel) level, livingTarget, 2);
                        instance.setCoolDown((int) CONFIG.purifyingWaterCooldown, mode);
                        instance.addMasteryPoint(entity);
                    }
                }
            }
        }

        if (mode == 1) {
            if (!(entity instanceof Player player)) return;
            if (level.isClientSide) return;

            CompoundTag tag = instance.getOrCreateTag();
            int points = tag.getInt("CreationElementalPoints");
            boolean isCrouching = player.isShiftKeyDown();

            if (isCrouching && points >= 200) {

                int range = instance.isMastered(player) ? 300 : 100;

                BlockPos waterPos = findWaterRaycast((ServerLevel) level, player, range);

                if (waterPos != null) {
                    player.teleportTo(waterPos.getX() + 0.5, waterPos.getY() + 1, waterPos.getZ() + 0.5);

                    points -= 200;
                    tag.putInt("CreationElementalPoints", points);

                    player.displayClientMessage(Component.literal("Synchronizing with the tides...").withStyle(ChatFormatting.BLUE), true);
                } else {
                    player.displayClientMessage(Component.literal("The tides do not answer your call...").withStyle(ChatFormatting.RED), true);
                }

                instance.setCoolDown((int) CONFIG.lilypadStepCooldown, mode);
                instance.addMasteryPoint(entity);

            } else {

                if (tag.getBoolean("Concealing")) {
                    tag.putBoolean("Concealing", false);
                    entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.PRESENCE_CONCEALMENT));
                    entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.GENERIC_UNCAST.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                } else {
                    tag.putBoolean("Concealing", true);
                    entity.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.PRESENCE_CONCEALMENT), 220, 0, false, false, false));
                    entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.PRESENCE_CONCEALMENT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
                }
            }
            return;
        }


        if (mode == 2) {
            if (!(entity instanceof Player player)) return;
            if (level.isClientSide) return;

            if (!instance.isMastered(player)) {
                player.displayClientMessage(Component.literal("You must master the tides to access higher power...").withStyle(ChatFormatting.LIGHT_PURPLE), true);
                return;
            }

            CompoundTag tag = instance.getOrCreateTag();
            int points = tag.getInt("CreationElementalPoints");

            if (points < 1000) {
                player.displayClientMessage(Component.literal("You lack sufficient creation energy... (Max required)").withStyle(ChatFormatting.RED), true);
                return;
            }

            ItemStack held = player.getMainHandItem();
            if (!held.isEmpty()) {

                List<EnchantmentInstance> list = EnchantmentHelper.getAvailableEnchantmentResults(30, held, level.registryAccess().registry(Registries.ENCHANTMENT).orElseThrow().holders().map(h -> h));

                if (!list.isEmpty()) {
                    EnchantmentInstance enchInstance = list.get(player.getRandom().nextInt(list.size()));

                    held.enchant(enchInstance.enchantment, 1);

                    points -= 1000;
                    tag.putInt("CreationElementalPoints", points);

                    player.inventoryMenu.broadcastChanges();

                    player.displayClientMessage(Component.literal("Lake's Blessing granted.").withStyle(ChatFormatting.AQUA), true);

                } else {
                    player.displayClientMessage(Component.literal("This item has reached its limit...").withStyle(ChatFormatting.RED), true);
                }
            }
        }
    }


    private void purify(LivingEntity entity, ServerLevel level) {
        if (entity instanceof LivingEntity targetPlayer) {
            IExistence existence = TensuraStorages.getExistenceFrom(targetPlayer);
            if (existence.getAlignment().equals(Alignment.MAJIN)) {
                existence.setAlignment(Alignment.DEFAULT);
            }
            spawnPurificationRing(level, targetPlayer);
        }
    }

    private void spawnPurificationRing(ServerLevel level, LivingEntity entity) {
        double radius = 2.0;
        int particleCount = 20;
        double height = entity.getY() + 0.5;

        for (int i = 0; i < particleCount; i++) {
            double angle = (2 * Math.PI * i) / particleCount;
            double x = entity.getX() + radius * Math.cos(angle);
            double z = entity.getZ() + radius * Math.sin(angle);

            level.sendParticles(ParticleTypes.CLOUD, x, height, z, 1, 0.0, 0.0, 0.0, 0.0);
            level.sendParticles(ParticleTypes.BUBBLE, x, height, z, 1, 0.0, 0.0, 0.0, 0.0);
        }
    }

    private void spawnPurifyParticles(ServerLevel level, Entity center, double size) {
        WorldParticleBuilder.create(LodestoneParticleTypes.WISP_PARTICLE).setScaleData(GenericParticleData.create(0.5f, 1.5f, 0f).setEasing(Easing.QUARTIC_OUT).build()).setTransparencyData(GenericParticleData.create(0.8f, 0f).build()).setColorData(ColorParticleData.create(Color.WHITE, new Color(100, 230, 255)).build()).setLifetime(20).addMotion(0, 0.1f, 0).enableForcedSpawn().spawn(level, center.getX(), center.getY() + 0.1, center.getZ());

        for (int i = 0; i < 15; i++) {
            double offsetX = (level.random.nextDouble() - 0.5) * size;
            double offsetZ = (level.random.nextDouble() - 0.5) * size;

            WorldParticleBuilder.create(LodestoneParticleTypes.SPARKLE_PARTICLE).setScaleData(GenericParticleData.create(0.2f, 0.4f, 0f).build()).setTransparencyData(GenericParticleData.create(1.0f, 0f).build()).setColorData(ColorParticleData.create(Color.WHITE, new Color(200, 230, 255)).build()).setLifetime(30 + level.random.nextInt(10)).addMotion(0, 0.05f + level.random.nextFloat() * 0.1f, 0).setSpinData(SpinParticleData.create(0.1f, 0.4f).build()).enableForcedSpawn().spawn(level, center.getX() + offsetX, center.getY(), center.getZ() + offsetZ);
        }
    }

    private BlockPos findNearestWater(ServerLevel level, BlockPos center, int range) {
        BlockPos closest = null;
        double minDist = Double.MAX_VALUE;
        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos pos = center.offset(x, y, z);
                    if (level.getFluidState(pos).is(Fluids.WATER)) {
                        double dist = center.distSqr(pos);
                        if (dist < minDist) {
                            minDist = dist;
                            closest = pos;
                        }
                    }
                }
            }
        }
        return closest;
    }

    private BlockPos findWaterRaycast(ServerLevel level, Player player, int range) {
        Vec3 start = player.getEyePosition();
        Vec3 look = player.getLookAngle().normalize();

        for (int i = 1; i <= range; i++) {
            Vec3 checkPos = start.add(look.scale(i));
            BlockPos center = BlockPos.containing(checkPos);

            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        BlockPos pos = center.offset(x, y, z);

                        if (level.getFluidState(pos).is(Fluids.WATER)) {
                            return pos;
                        }
                    }
                }
            }
        }
        return null;
    }
}








































