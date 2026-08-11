package com.radientfox.stellarprism.ability.Unique;

import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InterloperSkill extends Skill {

    public static final Set<UUID> PHASING = ConcurrentHashMap.newKeySet();
    private static final ResourceKey<Level> SUBSPACE = ResourceKey.create(net.minecraft.core.registries.Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("stellarprism", "subspace"));
    private static final ResourceLocation INTERLOPER = ResourceLocation.fromNamespaceAndPath("stellarprism", "interloper");
    private static final ResourceLocation COOK = ResourceLocation.fromNamespaceAndPath("tensura", "cook");
    private static final ResourceLocation TIME_TRAVELER = ResourceLocation.fromNamespaceAndPath("trnightmare", "time_traveler");
    private static final Set<ResourceLocation> INCOMPATIBLE_SKILLS = Set.of(ResourceLocation.fromNamespaceAndPath("trnightmare", "stasis"), ResourceLocation.fromNamespaceAndPath("trnightmare", "inversion"), ResourceLocation.fromNamespaceAndPath("tensura", "infinity_prison"), ResourceLocation.fromNamespaceAndPath("tensura", "oppressor"), ResourceLocation.fromNamespaceAndPath("tensura", "suppressor"), ResourceLocation.fromNamespaceAndPath("tensura", "creator"));
    private static final int RECALL_TIME = 90;
    private static final int PHASE_DURATION_TICKS = 200;
    private static final int MASTERED_PHASE_DURATION_TICKS = 600;
    private static final int PHASE_COOLDOWN_TICKS = 15;
    private static final int DEATH_SAVE_COOLDOWN_TICKS = 600;
    private static final int SUBSPACE_RANDOM_RADIUS = 1000;
    private static final Set<UUID> WAS_INVULNERABLE = ConcurrentHashMap.newKeySet();

    public InterloperSkill() {
        super(Skill.SkillType.UNIQUE);
    }

    @Override
    public @Nullable ResourceLocation getSkillIcon() {
        return ResourceLocation.fromNamespaceAndPath("stellarprism", "textures/skill/unique/unity.png");
    }

    @Override
    public MutableComponent getSkillDescription() {
        return Component.literal("<pulse base=0.9 a=0.9 f=1.5>§5Stay in character. <glitch f=3 j=0.02 b=0.01 s=0.1>§5STAY IN CHARACTER.");
    }

    @Override
    public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
        return true;
    }

    @Override
    public void onTick(ManasSkillInstance instance, LivingEntity entity) {
        super.onTick(instance, entity);

        if (hasIncompatibleSkill(entity)) {
            removeInterloper(entity);
            return;
        }

        if (entity instanceof ServerPlayer player && PHASING.contains(player.getUUID())) {

            if (!player.getAbilities().flying) {
                player.getAbilities().flying = true;
                player.onUpdateAbilities();
            }
        }

        if (!(entity instanceof ServerPlayer player)) return;
        if (player.level().isClientSide()) return;
        CompoundTag tag = instance.getOrCreateTag();

        if (player.level().dimension().equals(SUBSPACE)) {
            tag.putInt("SubspaceTimer", 0);
            return;
        }

        int timer = tag.getInt("SubspaceTimer") + 1;
        tag.putInt("SubspaceTimer", timer);

        if (timer >= RECALL_TIME) {

            ServerLevel subspace = player.server.getLevel(SUBSPACE);
            if (subspace != null) {

                BlockPos targetPos = getRandomSubspacePosition(subspace);

                player.teleportTo(subspace, targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, player.getYRot(), player.getXRot());

                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 220, 9, false, false, false));
                instance.setCoolDown(600, 0);

                tag.putInt("SubspaceTimer", 0);

                player.displayClientMessage(Component.literal("The Subspace has reclaimed you.").withStyle(ChatFormatting.DARK_PURPLE), true);
            }
        }
        int secondsLeft = (RECALL_TIME - timer) * 5;

        player.displayClientMessage(Component.literal("Subspace Recall: " + secondsLeft + "s").withStyle(ChatFormatting.LIGHT_PURPLE), true);
    }

    @Override
    public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
        return true;
    }

    @Override
    public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
        if (entity instanceof ServerPlayer player) {
            AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.RESISTANCE_DEGRADATION, INTERLOPER, 1.0D, AttributeModifier.Operation.ADD_VALUE);

            MinecraftServer server = player.server;
            PlayerList playerList = server.getPlayerList();

            ClientboundPlayerInfoRemovePacket removePkt = new ClientboundPlayerInfoRemovePacket(List.of(player.getUUID()));

            Component leaveMsg = Component.translatable("multiplayer.player.left", player.getDisplayName()).withStyle(ChatFormatting.YELLOW);

            for (ServerPlayer target : playerList.getPlayers()) {
                MobEffectInstance effect = target.getEffect(TensuraMobEffects.PRESENCE_SENSE);

                if (server.getProfilePermissions(target.getGameProfile()) < 2 || (effect != null && effect.getAmplifier() > 1)) {
                    target.connection.send(removePkt);
                    target.sendSystemMessage(leaveMsg);
                }
            }
        }
    }

    @Override
    public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
        if (entity instanceof ServerPlayer player) {
            AttributeHelper.removeAttribute(entity, TensuraAttributes.RESISTANCE_DEGRADATION, INTERLOPER);

            MinecraftServer server = player.server;
            PlayerList playerList = server.getPlayerList();

            ClientboundPlayerInfoUpdatePacket addPkt = ClientboundPlayerInfoUpdatePacket.createPlayerInitializing(List.of(player));

            Component joinMsg = Component.translatable("multiplayer.player.joined", player.getDisplayName()).withStyle(ChatFormatting.YELLOW);

            for (ServerPlayer target : playerList.getPlayers()) {
                MobEffectInstance effect = target.getEffect(TensuraMobEffects.PRESENCE_SENSE);

                if (server.getProfilePermissions(target.getGameProfile()) < 2 || (effect != null && effect.getAmplifier() > 1)) {
                    target.connection.send(addPkt);
                    target.sendSystemMessage(joinMsg);
                }
            }
        }
    }

    @Override
    public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity attacker, LivingEntity entity, DamageSource source, Changeable<Float> amount) {
        if (!this.isInSlot(attacker, instance)) {
            return true;
        }

        if (entity.getType() == EntityType.ENDERMAN && entity.getHealth() <= amount.get() && attacker instanceof ServerPlayer player) {

            Skills skills = SkillAPI.getSkillsFrom(player);

            if (skills.getSkill(COOK).isEmpty() && player.getRandom().nextFloat() < 0.01F) {
                if (skills.learnSkill(COOK)) {
                    player.displayClientMessage(Component.literal("<shake a=2 f=3>Something is wrong... what in the world did you do?").withStyle(ChatFormatting.DARK_PURPLE), true);
                }
            }
            if (skills.getSkill(TIME_TRAVELER).isEmpty() && player.getRandom().nextFloat() < 0.01F) {
                if (skills.learnSkill(TIME_TRAVELER)) {
                    player.displayClientMessage(Component.literal("<glitch f=3 j=0.02 b=0.01 s=0.1>You feel your molecules become more and more distorted...").withStyle(ChatFormatting.BLUE), true);
                }
            }
        }

        if (entity.getAttributeValue(TensuraAttributes.LAW_DEGRADATION) > 0.0D && EnergyHelper.getMaxEP(entity) > EnergyHelper.getMaxEP(attacker) * 2.0D) {
            return true;
        }

        AttributeInstance attribute = entity.getAttribute(TensuraAttributes.MULTILAYER_BARRIER);

        if (attribute != null && !attribute.getModifiers().isEmpty()) {
            attribute.removeModifiers();

            entity.level().playSound(null, entity.blockPosition(), TensuraSoundEvents.BARRIER_BREAK.get(), SoundSource.AMBIENT, 1.0F, 1.0F);
        }

        return true;
    }

    public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
        super.onLearnSkill(instance, entity);
        if (!(instance.getMastery() < (double) 0.0F) && !instance.isTemporarySkill()) {
            TensuraSkillInstance eye = new TensuraSkillInstance(ExtraSkills.LAW_MANIPULATION.get());
            eye.getOrCreateTag().putBoolean("NoMagiculeCost", true);
            SkillHelper.learnSkill(entity, eye);
        }
    }

    @Override
    public int getModes(ManasSkillInstance instance) {
        return 2;
    }

    @Override
    public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
        if (reverse) {
            return mode == 0 ? 1 : 0;
        }

        return mode == 1 ? 0 : 1;
    }

    @Override
    public String getModeId(ManasSkillInstance instance, int mode) {
        return switch (mode) {
            case 0 -> "interloper.visitor";
            case 1 -> "interloper.enter_the_void";
            default -> super.getModeId(instance, mode);
        };
    }

    @Override
    public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
        switch (mode) {
            case 0 -> teleportToSubspace(instance, entity);

            case 1 -> {
            }
        }
    }

    private void teleportToSubspace(ManasSkillInstance instance, LivingEntity entity) {
        if (!(entity instanceof ServerPlayer player)) return;

        ServerLevel destination;

        if (player.level().dimension().equals(SUBSPACE)) {
            destination = player.server.getLevel(Level.OVERWORLD);
        } else {
            destination = player.server.getLevel(SUBSPACE);
        }

        if (destination == null) return;

        BlockPos targetPos = destination.dimension() == Level.OVERWORLD ? player.server.overworld().getSharedSpawnPos() : new BlockPos(0, 100, 0);

        AABB area = player.getBoundingBox().inflate(3.0);

        for (Entity nearby : player.level().getEntities(player, area)) {

            if (nearby instanceof ServerPlayer other) {

                other.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 220, 9, false, false, false));

                other.teleportTo(destination, targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, other.getYRot(), other.getXRot());

            } else if (nearby instanceof LivingEntity living) {

                living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 220, 9, false, false, false));

                living.changeDimension(new DimensionTransition(destination, targetPos.getCenter(), Vec3.ZERO, living.getYRot(), living.getXRot(), DimensionTransition.DO_NOTHING));
            }
        }

        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 220, 9, false, false, false));

        player.teleportTo(destination, targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, player.getYRot(), player.getXRot());

        instance.addMasteryPoint(entity);
        instance.setCoolDown(60, 0);
    }

    @Override
    public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
        if (mode != 1) return false;

        if (!(entity instanceof ServerPlayer player)) return false;

        UUID uuid = player.getUUID();

        int phaseDuration = instance.getMastery() >= instance.getMaxMastery() ? MASTERED_PHASE_DURATION_TICKS : PHASE_DURATION_TICKS;

        if (heldTicks >= phaseDuration) {
            endPhasing(player);

            instance.setCoolDown(PHASE_COOLDOWN_TICKS, 1);

            return false;
        }
        if (!PHASING.contains(uuid)) {
            if (player.isInvulnerable()) {
                WAS_INVULNERABLE.add(uuid);
            } else {
                WAS_INVULNERABLE.remove(uuid);
            }
        }

        PHASING.add(uuid);

        player.setInvulnerable(true);

        player.getAbilities().mayfly = true;
        player.getAbilities().flying = true;
        player.onUpdateAbilities();

        int remainingTicks = phaseDuration - heldTicks;
        int remainingSeconds = (remainingTicks + 19) / 20;

        player.displayClientMessage(Component.literal("Phasing: " + remainingSeconds + "s").withStyle(ChatFormatting.GOLD), true);

        return true;
    }

    @Override
    public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
        if (!(entity instanceof ServerPlayer player)) return;

        if (PHASING.contains(player.getUUID())) {
            endPhasing(player);

            instance.setCoolDown(PHASE_COOLDOWN_TICKS, 1);
            instance.addMasteryPoint(entity);
        }
    }

    private void endPhasing(ServerPlayer player) {
        UUID uuid = player.getUUID();

        PHASING.remove(uuid);

        player.setInvulnerable(WAS_INVULNERABLE.remove(uuid));

        player.getAbilities().flying = false;
        player.getAbilities().mayfly = false;
        player.onUpdateAbilities();
        ejectFromBlocks(player);
    }

    private void ejectFromBlocks(ServerPlayer player) {
        BlockPos.MutableBlockPos pos = player.blockPosition().mutable();

        for (int y = 0; y < 8; y++) {
            pos.set(player.getX(), player.getY() + y, player.getZ());

            if (player.level().getBlockState(pos).isAir()) {
                player.teleportTo(player.serverLevel(), pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, player.getYRot(), player.getXRot());
                return;
            }
        }
    }

    private BlockPos getRandomSubspacePosition(ServerLevel subspace) {
        RandomSource random = subspace.getRandom();

        for (int attempts = 0; attempts < 50; attempts++) {
            int x = random.nextIntBetweenInclusive(-SUBSPACE_RANDOM_RADIUS, SUBSPACE_RANDOM_RADIUS);

            int z = random.nextIntBetweenInclusive(-SUBSPACE_RANDOM_RADIUS, SUBSPACE_RANDOM_RADIUS);

            int y = subspace.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);

            if (y <= subspace.getMinBuildHeight() || y >= subspace.getMaxBuildHeight() - 2) {
                continue;
            }

            BlockPos pos = new BlockPos(x, y, z);


            if (subspace.getBlockState(pos).isAir() && subspace.getBlockState(pos.above()).isAir()) {
                return pos;
            }
        }

        return new BlockPos(0, 100, 0);
    }

    @Override
    public boolean onDeath(ManasSkillInstance instance, LivingEntity entity, DamageSource source) {
        if (!(entity instanceof ServerPlayer player)) {
            return true;
        }

        if (instance.getCoolDown(0) > 0) {
            return true;
        }

        ServerLevel subspace = player.server.getLevel(SUBSPACE);

        if (subspace == null) {
            return true;
        }

        BlockPos targetPos = getRandomSubspacePosition(subspace);

        player.setHealth(Math.max(player.getMaxHealth() * 0.10F, 1.0F));
        player.removeAllEffects();
        player.clearFire();
        player.invulnerableTime = 60;

        instance.setCoolDown(DEATH_SAVE_COOLDOWN_TICKS, 0);

        player.teleportTo(subspace, targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, player.getYRot(), player.getXRot());

        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 220, 9, false, false, false));

        instance.addMasteryPoint(entity);

        if (subspace != null) {
            subspace.sendParticles(ParticleTypes.PORTAL, targetPos.getX() + 0.5, targetPos.getY() + 1.0, targetPos.getZ() + 0.5, 40, 1.0, 1.0, 1.0, 0.1);
        }

        return false;
    }

    private boolean hasIncompatibleSkill(LivingEntity entity) {
        Skills skills = SkillAPI.getSkillsFrom(entity);

        for (ManasSkillInstance skill : skills.getLearnedSkills()) {
            if (skill == null || skill.getSkill() == null) {
                continue;
            }

            ResourceLocation id = skill.getSkill().getRegistryName();

            if (id != null && INCOMPATIBLE_SKILLS.contains(id)) {
                return true;
            }
        }

        return false;
    }

    private void removeInterloper(LivingEntity entity) {
        if (!(entity instanceof ServerPlayer player)) {
            return;
        }

        SkillAPI.getSkillsFrom(player).forgetSkill(INTERLOPER);

        PHASING.remove(player.getUUID());
        WAS_INVULNERABLE.remove(player.getUUID());

        player.setInvulnerable(false);
        player.getAbilities().flying = false;
        player.getAbilities().mayfly = false;
        player.onUpdateAbilities();
        player.displayClientMessage(Component.literal("You feel your molecules begin to stabilize... was this worth it?").withStyle(ChatFormatting.DARK_BLUE), true);

    }
}
