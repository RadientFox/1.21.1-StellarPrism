package com.radientfox.stellarprism.ability.Unique;

import com.radientfox.stellarprism.Registry.main.StellarEffects;
import com.radientfox.stellarprism.config.skills.StellarUniqueConfig;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.extra.ThoughtAccelerationSkill;
import io.github.manasmods.tensura.entity.projectile.TensuraFlyingProjectile;
import io.github.manasmods.tensura.entity.projectile.magic.WindTornadoProjectile;
import io.github.manasmods.tensura.registry.TensuraStats;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.AttributeHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import static com.github.hvnbael.trnightmare.main.uniques.InfinitySkill.ACCELERATION;

public class JadeSkill extends Skill {
    private static final StellarUniqueConfig.Jade CONFIG = ConfigRegistry.getConfig(StellarUniqueConfig.class).Jade;
    Boolean auraUnlock = false;

    public JadeSkill() {
        super(SkillType.UNIQUE);
    }

    //Non Crucial Stuff
    public @Nullable ResourceLocation getSkillIcon() {
        return ResourceLocation.fromNamespaceAndPath("stellarprism", "textures/skill/unique/jade.png");
    }

    public double getDefaultAcquiringMagiculeCost() {
        return CONFIG.mpAcquirement;
    }

    @Override
    public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
        return true;
    }

    public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
        if (!(instance.getMastery() < (double) 0.0F) && !instance.isTemporarySkill()) {
            TensuraSkillInstance eye = new TensuraSkillInstance(ExtraSkills.MAGIC_WIND_TRANSFORM.get());
            eye.getOrCreateTag().putBoolean("NoMagiculeCost", true);
            SkillHelper.learnSkill(entity, eye);
        }
    }


    //The Annoyings
    public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
        AttributeHelper.multiplyChantSpeed(entity, 2);
        ThoughtAccelerationSkill.onToggle(instance, entity, ACCELERATION, true);

        if (SkillUtils.hasSkillFully(entity, ExtraSkills.WIND_DOMINATION.get())
                && SkillUtils.isSkillMastered(entity, (ManasSkill) ExtraSkills.WIND_DOMINATION)) {

            if (SkillUtils.hasSkillFully(entity, ExtraSkills.MOLECULAR_MANIPULATION.get())
                    && SkillUtils.isSkillMastered(entity, (ManasSkill) ExtraSkills.MOLECULAR_MANIPULATION)) {

                AttributeHelper.multiplyElementalBoost(entity, TensuraAttributes.WIND_BOOST, CONFIG.moleWind);

            } else {


                AttributeHelper.multiplyElementalBoost(entity, TensuraAttributes.WIND_BOOST, CONFIG.windDom);
            }
        }

        if (entity instanceof Player player) {
            if (!player.getAbilities().mayfly) {
                player.getAbilities().mayfly = true;
                player.onUpdateAbilities();
            }
        }
    }

    public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
        AttributeHelper.removeChantSpeed(entity, 2);
        ThoughtAccelerationSkill.onToggle(instance, entity, ACCELERATION, false);

        if (SkillUtils.hasSkillFully(entity, ExtraSkills.WIND_DOMINATION.get())
                && SkillUtils.isSkillMastered(entity, (ManasSkill) ExtraSkills.WIND_DOMINATION)) {

            if (SkillUtils.hasSkillFully(entity, ExtraSkills.MOLECULAR_MANIPULATION.get())
                    && SkillUtils.isSkillMastered(entity, (ManasSkill) ExtraSkills.MOLECULAR_MANIPULATION)) {

                AttributeHelper.removeElementalMultiplier(entity, TensuraAttributes.WIND_BOOST, CONFIG.moleWind);

            } else {

                AttributeHelper.removeElementalMultiplier(entity, TensuraAttributes.WIND_BOOST, CONFIG.windDom);
            }
        }

        if (entity instanceof Player player) {
            if (!player.getAbilities().mayfly) {
                player.getAbilities().mayfly = true;
                player.onUpdateAbilities();
            }
        }
    }

    //Modes
    public int getModes(ManasSkillInstance instance) {
        return 2;
    }

    @Override
    public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
        if (reverse) return (mode == 0) ? 1 : (mode - 1);

        return (mode == 1) ? 0 : (mode + 1);
    }

    public String getModeId(ManasSkillInstance instance, int mode) {
        String var10000;
        switch (mode) {
            case 0 -> var10000 = "jade.spiral";
            case 1 -> var10000 = "jade.empower";
            default -> var10000 = super.getModeId(instance, mode);
        }
        return var10000;
    }

    public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
        double var10000;
        switch (mode) {
            case 0 -> var10000 = CONFIG.spiralWindMP;
            case 1 -> var10000 = CONFIG.empoweringWindMP;
            default -> var10000 = 0.0;
        }

        return var10000;
    }

    @Override
    public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {

        switch (mode) {

            //Spiral Winds
            case 0:
                entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BREEZE_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
                entity.swing(InteractionHand.MAIN_HAND, true);

                WindTornadoProjectile blade = new WindTornadoProjectile(entity.level(), entity);
                blade.setSpeed(1.0F);
                blade.setBurstDelay(CONFIG.burstDelay + 13);
                if (instance.isMastered(entity)) {
                    blade.setHitRadius(CONFIG.hitRadiusMastered);
                    blade.setDamage(CONFIG.windDamageMastered);
                    blade.setSecondaryDamage(CONFIG.magicDamageMastered);
                    blade.setPullForce(CONFIG.pullForce);
                    blade.setOnExplodeBlades(CONFIG.bladeNumber);
                    blade.setBladeDamage(CONFIG.bladeDamage);
                } else {
                    blade.setHitRadius(CONFIG.hitRadius);
                    blade.setDamage(CONFIG.windDamage);
                    blade.setSecondaryDamage(CONFIG.magicDamage);
                }

                blade.setSkill(entity, instance, this, mode);
                blade.setNoGravity(true);
                blade.setPos(entity.getEyePosition().add(entity.getLookAngle().normalize()));
                blade.shootFromRot(entity.getLookAngle());
                entity.level().addFreshEntity(blade);
                entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BREEZE_WHIRL, SoundSource.PLAYERS, 1.0F, 1.0F);

                instance.setCoolDown((int) CONFIG.spiralWindsCooldown, mode);
                break;

            //Empowering winds or something
            case 1:

                boolean spiritReq = false;
                boolean battleReq = false;

                if (auraUnlock) {

                    Player player = (Player) entity;

                    Holder<MobEffect> aura = TensuraMobEffects.getReference((RegistrySupplier<MobEffect>) StellarEffects.WINDAURA);

                    entity.addEffect(new MobEffectInstance(aura, 600, 0, false, false, true));

                    player.displayClientMessage(Component.translatable("stellarprism.skill.mode.jade.active").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)), false);

                    entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.CAST_WIND, SoundSource.PLAYERS, 1.0F, 1.0F);

                    instance.setCoolDown((int) CONFIG.empoweringWindCooldown, mode);

                } else {

                    if (entity instanceof ServerPlayer player) {

                        player.displayClientMessage(Component.translatable("stellarprism.skill.mode.jade.lock").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), false);

                        if (CONFIG.needsSpirit == (TensuraStorages.getSpiritFrom(entity).getSpiritLevelId(Element.WIND) >= 3)) {
                            spiritReq = true;
                        }

                        int battlewills = player.getStats().getValue(Stats.CUSTOM.get(TensuraStats.BATTLEWILL_MASTERED));

                        if (battlewills >= CONFIG.windBattlewills) {
                            battleReq = true;
                        }
                    }

                    if (spiritReq && battleReq) {
                        auraUnlock = true;
                    }
                }

                break;
        }
    }
}
//Thank the gods for autoformatting shortcuts