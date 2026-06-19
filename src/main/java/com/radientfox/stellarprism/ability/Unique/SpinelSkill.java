package com.radientfox.stellarprism.ability.Unique;

import com.github.hvnbael.trnightmare.compat.TextAnimatorCompat;
import com.radientfox.stellarprism.Registry.main.StellarEffects;
import com.radientfox.stellarprism.config.skills.StellarUniqueConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.subclass.ISpatialStorage;
import io.github.manasmods.tensura.entity.projectile.TensuraFlyingProjectile;
import io.github.manasmods.tensura.entity.projectile.magic.SpaceCutProjectile;
import io.github.manasmods.tensura.menu.container.SpatialStorageContainer;
import io.github.manasmods.tensura.registry.TensuraStats;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.AttributeHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpinelSkill extends Skill  implements ISpatialStorage {
    private static final StellarUniqueConfig.Spinel CONFIG = ConfigRegistry.getConfig(StellarUniqueConfig.class).Spinel;
    public static final ResourceLocation SPINEL = ResourceLocation.fromNamespaceAndPath("stellarprism", "spinel_skill");

    public SpinelSkill() {
        super(SkillType.UNIQUE);
    }

    public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
        return true;
    }

    public int getMaxMastery() {
        return (int) CONFIG.masteryPoints;
    }

    public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
        return instance.getMastery() >= 0.0;
    }

    public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
        super.onLearnSkill(instance, entity);
        if (CONFIG.spaceTransform) {
            if (!(instance.getMastery() < 0.0) && !instance.isTemporarySkill()) {
                    SkillHelper.learnSkill(entity, (ManasSkill) ExtraSkills.MAGIC_SPACE_TRANSFORM);

            }
        }
    }
    public @Nullable MutableComponent getColoredName() {
        MutableComponent name = this.getName();
        if (name == null) {
            return null;
        } else {
            return TextAnimatorCompat.useOnClient() ? TextAnimatorCompat.skillAnimatedDisplayName(name, ResourceLocation.fromNamespaceAndPath("stelarprism", "spinel_skill")) : name.withStyle(ChatFormatting.WHITE);
        }
    }

    public double getDefaultAcquiringMagiculeCost() {
        return CONFIG.mpAcquirement;
    }

    public int getModes(ManasSkillInstance instance) {
        return 3;
    }

    public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {

            if (reverse) {
                return mode == 0 ? 2 : mode - 1;
            } else {
                return mode == 2 ? 0 : mode + 1;
            }


    }

    public @Nullable ResourceLocation getSkillIcon() {
        return ResourceLocation.fromNamespaceAndPath("stellarprism", "textures/skill/unique/spinel_skill.png");
    }

    public String getModeId(ManasSkillInstance instance, int mode) {
        String var10000;
        switch (mode) {
            case 0 -> var10000 = "spinel.cutter";
            case 1 -> var10000 = "spinel.storage";
            case 2 -> var10000 = "spinel.aura";
            default -> var10000 = super.getModeId(instance, mode);
        }

        return var10000;
    }

    public double getAuraCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
        double var10000;
        switch (mode) {
            case 0 -> var10000 = CONFIG.apSpaceCutter;
            case 1 -> var10000 = CONFIG.apPocketStorage;
            default -> var10000 = 0.0;
        }

        return var10000;
    }

    public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
        if (SkillUtils.hasSkillFully(entity, (ManasSkill) ExtraSkills.SPATIAL_DOMINATION.get()) && SkillUtils.isSkillMastered(entity, (ManasSkill) ExtraSkills.SPATIAL_DOMINATION)) {
            if (SkillUtils.hasSkillFully(entity, (ManasSkill) ExtraSkills.MOLECULAR_MANIPULATION.get()) && SkillUtils.isSkillMastered(entity, (ManasSkill) ExtraSkills.MOLECULAR_MANIPULATION)) {
                AttributeHelper.multiplyElementalBoost(entity, TensuraAttributes.SPACE_BOOST, CONFIG.molecManip);
            }
            else {
                AttributeHelper.multiplyElementalBoost(entity, TensuraAttributes.SPACE_BOOST, CONFIG.spaceDom);
            }
        }

    }

    public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
        if (SkillUtils.hasSkillFully(entity, (ManasSkill) ExtraSkills.SPATIAL_DOMINATION.get()) && SkillUtils.isSkillMastered(entity, (ManasSkill) ExtraSkills.SPATIAL_DOMINATION)) {
            if (SkillUtils.hasSkillFully(entity, (ManasSkill) ExtraSkills.MOLECULAR_MANIPULATION.get()) && SkillUtils.isSkillMastered(entity, (ManasSkill) ExtraSkills.MOLECULAR_MANIPULATION)) {
                AttributeHelper.removeElementalMultiplier(entity, TensuraAttributes.SPACE_BOOST, CONFIG.molecManip);

            }
            else {
                AttributeHelper.removeElementalMultiplier(entity, TensuraAttributes.SPACE_BOOST, CONFIG.spaceDom);
            }
        }
    }

    Boolean auraUnlock = false;

    public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {

        boolean spiritReq = false;
        boolean battleReq = false;
            switch (mode) {
                case 0:
                    entity.level().playSound((Player)null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
                    entity.swing(InteractionHand.MAIN_HAND, true);
                    SpaceCutProjectile blade = new SpaceCutProjectile(entity.level(), entity);
                    blade.setSpeed(1.5F);
                    blade.setVisible(true);
                    blade.setPiercingEntity(true);
                    blade.setPiercingBlock(true);
                    blade.setDamage((float) (instance.isMastered(entity) ? CONFIG.masterydmgSpaceCutter : CONFIG.dmgSpaceCutter));
                    blade.setSize(5.0F);
                    blade.setSkill(entity, instance, this, mode);
                    blade.setNoGravity(true);
                    blade.setPosDirection(entity, TensuraFlyingProjectile.PositionDirection.MIDDLE);
                    blade.shootFromRot(entity.getLookAngle());
                    entity.level().addFreshEntity(blade);
                    instance.addMasteryPoint(entity);

                    instance.setCoolDown((int) CONFIG.spaceCutterCooldown, mode);

                    break;

                case 1:
                    this.openSpatialStorage(entity, instance);
                    break;

                case 2:
                    if (auraUnlock) {

                        Player player = (Player)entity;
                        entity.addEffect(new MobEffectInstance(StellarEffects.SPACEAURA, 300, 0, false, false, false));

                        instance.setCoolDown((int) CONFIG.spaceAuraCooldown, mode);
                            entity.level().playSound((Player) null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.CAST_SPACE, SoundSource.PLAYERS, 1.0F, 1.0F);
                            player.displayClientMessage(Component.translatable("stellarprism.skill.mode.spinel.active").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), false);
/*
                    if (entity.hasEffect(StellarEffects.ONYXAURA.get())){
                        entity.removeEffect((MobEffect) StellarEffects.ONYXAURA.get());
                    }
                    if (entity.hasEffect(StellarEffects.OPALAURA.get())){
                        entity.removeEffect((MobEffect) StellarEffects.OPALAURA.get());
                    }
                    if (entity.hasEffect(StellarEffects.RUBYAURA.get())){
                        entity.removeEffect((MobEffect) StellarEffects.RUBYAURA.get());
                    }
                    if (entity.hasEffect(StellarEffects.JADEAURA.get())){
                        entity.removeEffect((MobEffect) StellarEffects.JADEAURA.get());
                    }
                    if (entity.hasEffect(StellarEffects.SOUNDAURA.get())){
                        entity.removeEffect((MobEffect) StellarEffects.SOUNDAURA.get());
                    }
                    if (entity.hasEffect(StellarEffects.GRAVITYAURA.get())){
                        entity.removeEffect((MobEffect) StellarEffects.GRAVITYAURA.get());
                    }
                    if (entity.hasEffect(StellarEffects.TOPAZAURA.get())){
                        entity.removeEffect((MobEffect) StellarEffects.TOPAZAURA.get());
                    }
                    if (entity.hasEffect(StellarEffects.AQUAAURA.get())){
                        entity.removeEffect((MobEffect) StellarEffects.AQUAAURA.get());
                    }

 */
                    }else {
                        if (entity instanceof ServerPlayer player) {

                        player.displayClientMessage(Component.translatable("stellarprism.skill.mode.spinel.lock").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), false);

                        player.displayClientMessage(Component.translatable("stellarprism.join.message.terra").setStyle(Style.EMPTY.withColor(ChatFormatting.WHITE)), false);

                        if (CONFIG.needsSpirit == (TensuraStorages.getSpiritFrom(entity).getSpiritLevelId(Element.SPACE) >= 3)){
                            spiritReq = true;
                        }

                        int battlewills = player.getStats().getValue(Stats.CUSTOM.get(TensuraStats.BATTLEWILL_MASTERED));

                        if (battlewills >= CONFIG.spaitialBattlewills){
                            battleReq = true;
                        }
                    }

                        if (spiritReq && battleReq){
                            auraUnlock = true;
                        }

                    }
                    break;


            }



    }


    public @NotNull SpatialStorageContainer getSpatialStorage(ManasSkillInstance instance, HolderLookup.Provider provide) {
        boolean mastered = instance.getMastery() >= (double)this.getMaxMastery();
        SpatialStorageContainer container = new SpatialStorageContainer(mastered ? CONFIG.spatialSlots + 4 : CONFIG.spatialSlots, mastered ? CONFIG.spatialStackMastered : CONFIG.spatialStack);
        container.fromTag(instance.getOrCreateTag().getList("SpatialStorage", 10), provide);
        return container;
    }

    public void onTick(ManasSkillInstance instance, LivingEntity entity) {

      //  SeveranceCompat.shouldCancelSeverance(entity, source);





    }






}
