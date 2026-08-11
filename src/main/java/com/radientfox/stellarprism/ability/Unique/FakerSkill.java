package com.radientfox.stellarprism.ability.Unique;


import com.radientfox.stellarprism.config.skills.StellarUniqueConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.enchantment.TensuraEnchantments;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.util.AttributeHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.function.Predicate;

public class FakerSkill extends Skill {

    private static final StellarUniqueConfig.Faker CONFIG = ConfigRegistry.getConfig(StellarUniqueConfig.class).Faker;

    private static final String TAG_ANALYSIS_MODE = "AnalysisMode";
    private static final String TAG_ANALYSIS_ACTIVE = "AnalysisActive";


    public FakerSkill() {
        super(SkillType.UNIQUE);
    }

    public @Nullable ResourceLocation getSkillIcon() {
        return ResourceLocation.fromNamespaceAndPath("stellarprism", "textures/skill/unique/faker.png");
    }

    @Override
    public MutableComponent getSkillDescription() {
        return Component.literal(" Analysis, Projection, Reinforcement. Though shallow copies of what others wield, these items fit perfectly in your hand. Nurture them. Mend them. Perhaps one day, they may even surpass their originals.");
    }

    public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
        return instance.isToggled();
    }

    @Override
    public int getModes(ManasSkillInstance instance) {
        return 3;
    }

    @Override
    public String getModeId(ManasSkillInstance instance, int mode) {
        return switch (mode) {
            case 0 -> "faker.analytical_appraisal";
            case 1 -> "faker.reinforcement";
            case 2 -> "faker.projection";
            default -> super.getModeId(instance, mode);
        };
    }

    @Override
    public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
        if (reverse) {
            return mode == 0 ? 2 : mode - 1;
        }

        return mode == 2 ? 0 : mode + 1;
    }


    @Override
    public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
    }

    @Override
    public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
        MobEffectInstance regeneration = entity.getEffect(TensuraMobEffects.getReference(TensuraMobEffects.INSTANT_REGENERATION));
        if (regeneration != null && regeneration.getAmplifier() < 1) {
            entity.removeEffect(regeneration.getEffect());
        }
    }

    @Override
    public void onTick(ManasSkillInstance instance, LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.INSTANT_REGENERATION), 240, 0, false, false, false));
    }

    @Override
    public void onSkillMastered(ManasSkillInstance instance, LivingEntity entity) {
        if (instance.isSubInstance()) return;

        TensuraSkillInstance skill = new TensuraSkillInstance(UniqueSkills.SEVERER.get());

        skill.setMastery(UniqueSkills.SEVERER.get().getAcquirementMastery(entity));

        SkillHelper.learnSkill(entity, skill);
    }

    @Override
    public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {

        if (entity.level().isClientSide()) return;

        if (!(entity instanceof ServerPlayer player)) return;

        instance.getOrCreateTag().putInt("CurrentMode", mode);

        player.displayClientMessage(Component.literal("Mode: " + mode), true);

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

                        player.displayClientMessage(Component.literal("Trace, Off."), true);
                    } else {

                        int level = isMastered(instance, entity) ? 30 : 20;

                        AttributeHelper.addAnalysisAttributes(player, level, 30);

                        data.putBoolean("AnalysisActive", true);

                        player.displayClientMessage(Component.literal("Trace, On."), true);
                    }
                }
            }

            case 1 -> reinforce(instance, player);
            case 2 -> performProjection(instance, player);
        }
    }

    private void reinforce(ManasSkillInstance instance, ServerPlayer player) {
        ItemStack item = player.getMainHandItem();

        if (item.isEmpty()) {
            player.displayClientMessage(Component.literal("Nothing to enhance...").withStyle(ChatFormatting.RED), true);
            return;
        }

        var enchantments = item.get(DataComponents.ENCHANTMENTS);

        if (enchantments == null || enchantments.isEmpty()) {
            player.displayClientMessage(Component.literal("This item lacks mystical potential.").withStyle(ChatFormatting.RED), true);
            return;
        }

        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(enchantments);

        for (var entry : enchantments.entrySet()) {
            var holder = entry.getKey();
            var enchantment = holder.value();

            int maxLevel = enchantment.getMaxLevel();

            mutable.set(holder, maxLevel);
        }

        item.set(DataComponents.ENCHANTMENTS, mutable.toImmutable());

        player.displayClientMessage(Component.literal("Enhance!").withStyle(ChatFormatting.GOLD), true);

        player.swing(InteractionHand.MAIN_HAND);

        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0F, 1.2F);

        instance.setCoolDown(60, 1);
    }

    private void performProjection(ManasSkillInstance instance, @UnknownNullability ServerPlayer caster) {
        Level level = caster.level();
        if (level.isClientSide()) return;

        double range = 30;
        Vec3 eyePos = caster.getEyePosition(1.0F);
        Vec3 lookVec = caster.getViewVector(1.0F);
        Vec3 end = eyePos.add(lookVec.scale(range));
        AABB searchBox = caster.getBoundingBox().expandTowards(lookVec.scale(range)).inflate(1.0D);

        Predicate<Entity> predicate = target -> target instanceof LivingEntity living && living != caster && !living.getMainHandItem().isEmpty();

        EntityHitResult hit = ProjectileUtil.getEntityHitResult(caster, eyePos, end, searchBox, predicate, range * range);

        if (hit == null || !(hit.getEntity() instanceof LivingEntity targetLiving)) {
            caster.displayClientMessage(Component.translatable("<glitch f=3 j=0.02 b=0.01 s=0.1>There is nothing there...").withStyle(ChatFormatting.RED), true);
            level.playSound(null, caster.blockPosition(), SoundEvents.VILLAGER_NO, SoundSource.PLAYERS, 1.0F, 1.0F);
            instance.setCoolDown(100, 2);
            return;
        }

        ItemStack targetItem = targetLiving.getMainHandItem();
        if (targetItem.isEmpty()) {
            caster.displayClientMessage(Component.translatable("<glitch f=3 j=0.02 b=0.01 s=0.1>There is nothing there...").withStyle(ChatFormatting.RED), true);
            instance.setCoolDown(100, 2);
            return;
        }

        String itemId = BuiltInRegistries.ITEM.getKey(targetItem.getItem()).toString();
        boolean isRestricted = StellarUniqueConfig.FakerRestrictedItems().contains(itemId);

        int enchantmentLevel = targetItem.getEnchantmentLevel(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(TensuraEnchantments.TSUKUMOGAMI));
        boolean hasTsukumogami = enchantmentLevel > 0;

        if (isRestricted || hasTsukumogami) {
            String translationKey = hasTsukumogami ? "<shadow r=1 a=0.6>This weapon rejects you, even as a replica..." : "<glitch f=3 j=0.02 b=0.01 s=0.1>This artifact is not one that can be so easily replicated...";

            caster.displayClientMessage(Component.translatable(translationKey).withStyle(ChatFormatting.RED), true);
            level.playSound(null, caster.blockPosition(), SoundEvents.VILLAGER_NO, SoundSource.PLAYERS, 1.0F, 1.0F);

            instance.setCoolDown(100, 2);
            return;
        }

        ItemStack projectedCopy = targetItem.copy();
        projectedCopy.setCount(1);

        if (caster.getMainHandItem().isEmpty()) {
            caster.setItemInHand(InteractionHand.MAIN_HAND, projectedCopy);
        } else if (!caster.getInventory().add(projectedCopy)) {
            caster.drop(projectedCopy, false);
        }

        caster.displayClientMessage(Component.translatable("You've succeeded in creating a replica of the target's item.", targetLiving.getDisplayName()).withStyle(ChatFormatting.GOLD), true);
        level.playSound(null, caster.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);

        instance.setCoolDown(300, 2);

        addMasteryPoint(instance, caster);
    }

    @Override
    public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
        return this.isMastered(instance, living);
    }
}