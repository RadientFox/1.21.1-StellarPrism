package com.radientfox.stellarprism.ability.Unique;

import com.github.hvnbael.trnightmare.compat.tensura.EngravingEnchantment;
import com.mojang.datafixers.util.Pair;
import com.radientfox.stellarprism.Registry.main.StellarEffects;
import com.radientfox.stellarprism.Registry.main.StellarItems;
import com.radientfox.stellarprism.config.skills.StellarUniqueConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.extra.HakiSkill;
import io.github.manasmods.tensura.ability.skill.extra.ThoughtAccelerationSkill;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.enchantment.TensuraEnchantmentHelper;
import io.github.manasmods.tensura.enchantment.TensuraEnchantments;
import io.github.manasmods.tensura.entity.magic.MagicCircle;
import io.github.manasmods.tensura.entity.magic.beam.BeamProjectile;
import io.github.manasmods.tensura.entity.magic.field.haki.HakiField;
import io.github.manasmods.tensura.entity.variant.MagicCircleVariant;
import io.github.manasmods.tensura.registry.entity.MiscEntityTypes;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.phys.Vec3;

import static com.github.hvnbael.trnightmare.main.uniques.InfinitySkill.ACCELERATION;


public class PendragonSkill extends Skill {
    private static final StellarUniqueConfig.Pendragon CONFIG = ConfigRegistry.getConfig(StellarUniqueConfig.class).Pendragon;
    private static final String LIGHT_MODE = "LightMode";

    public PendragonSkill() {
        super(SkillType.UNIQUE);
    }

    public static boolean activatePendragonHaki(
            ManasSkillInstance instance,
            LivingEntity entity,
            int mode,
            int heldTicks,
            TensuraSkill skill) {

        IExistence existence = TensuraStorages.getExistenceFrom(entity);


        double souls = existence.getSoulPoints();


        float multiplier = 1.0F + (float) (souls / 100.0D);

        return HakiSkill.summonHaki(
                instance,
                entity,
                mode,
                heldTicks,
                skill,
                HakiField.HakiVariant.HERO,
                multiplier
        );
    }

    @Override
    public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
        return true;
    }

    @Override
    public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
        return true;
    }

    public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
        super.onLearnSkill(instance, entity);
        if (!(instance.getMastery() < (double) 0.0F) && !instance.isTemporarySkill()) {
            IExistence existence = TensuraStorages.getExistenceFrom(entity);
            existence.setBlessed(true);
            existence.markDirty();
            TensuraSkillInstance eye = new TensuraSkillInstance(ExtraSkills.HEAVENLY_EYE.get());
            eye.getOrCreateTag().putBoolean("NoMagiculeCost", true);
            SkillHelper.learnSkill(entity, eye);
        }
    }

    public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
        if (instance.isToggled() && (TensuraDamageHelper.isHoly(source) || TensuraDamageHelper.isLightDamage(source))) {
            float mult = instance.isMastered(owner) ? 3.0F : 2.0F;
            amount.set(amount.get() * mult);
            this.addMasteryPoint(instance, owner);
        }

        return true;
    }

    public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
        AttributeHelper.multiplyChantSpeed(entity, 2);
        ThoughtAccelerationSkill.onToggle(instance, entity, ACCELERATION, true);
    }

    public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
        AttributeHelper.removeChantSpeed(entity, 2);
        ThoughtAccelerationSkill.onToggle(instance, entity, ACCELERATION, false);
    }

    public void onTick(ManasSkillInstance instance, LivingEntity entity) {
        if (!instance.isToggled()) return;
        if (entity.level().isClientSide) return;
        if (!(entity instanceof Player player)) return;
        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();

        if (mainHand.getItem() instanceof SwordItem) {
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 120, 5, false, false, false));
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 120, 1, false, false, false));
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 120, 0, false, false, false));
        }
        if (offHand.getItem() instanceof ShieldItem) {
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 120, 1, false, false, false));
        }
        if (mainHand.getItem() instanceof PickaxeItem) {
            entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 120, 1, false, false, false));
        } else {
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 120, 0, false, false, false));
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
            case 0 -> "pendragon.summon";
            case 1 -> "pendragon.holy_order";
            case 2 -> "pendragon.holy_ray";
            case 3 -> "pendragon.holy_aura";
            default -> super.getModeId(instance, mode);
        };
    }

    @Override
    public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
        return switch (mode) {
            case 0 -> CONFIG.summonCost;
            case 1 -> CONFIG.holyOrderCost;
            case 2 -> CONFIG.holyRayCost;
            case 3 -> CONFIG.holyAuraCost;
            default -> 0.0D;
        };
    }

    @Override
    public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
        if (entity.level().isClientSide) return;
        if (!(entity instanceof Player player)) return;

        if (mode == 0) {
            ItemStack excalibur = new ItemStack(StellarItems.EXCALIBUR.get());
            EngravingEnchantment.engrave(excalibur, TensuraEnchantmentHelper.getEnchantment(entity.level(), TensuraEnchantments.TSUKUMOGAMI), 1);
            player.addItem(excalibur);
            instance.addMasteryPoint(entity);
        }
        if (mode == 1) {
            //Random bullshit ill do later
        }
        if (mode == 2) {
            instance.getOrCreateTag().putInt("BeamID", 0);
            instance.markDirty();
            return;
        }
        if (mode == 3) {

            CompoundTag skillTag = instance.getOrCreateTag();

            if (player.isShiftKeyDown()) {

                boolean light = !skillTag.getBoolean("LightMode");
                skillTag.putBoolean("LightMode", light);
                instance.markDirty();

                player.displayClientMessage(
                        Component.literal(light ? "Light Haki" : "Holy Haki"),
                        true
                );

                return;
            }

            if (!player.hasEffect(StellarEffects.PENDRAGON_AURA)) {

                MobEffectInstance aura =
                        new MobEffectInstance(
                                StellarEffects.PENDRAGON_AURA,
                                Integer.MAX_VALUE,
                                0,
                                false,
                                false,
                                false
                        );

                player.getPersistentData().putBoolean(
                        "PendragonLightMode",
                        skillTag.getBoolean("LightMode")
                );

                player.addEffect(aura);

            } else {

                player.removeEffect(StellarEffects.PENDRAGON_AURA);

            }

        }
    }

    //This is possibly the laziest way I could have gone about making this beam.
    @Override
    public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {

        if (mode == 1) {
            if (heldTicks % 20 == 0 && EnergyHelper.isOutOfEnergy(entity, instance, mode))
                return false;

            if (heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0 && heldTicks > 0)
                instance.addMasteryPoint(entity);

            return activatePendragonHaki(instance, entity, mode, heldTicks, this);
        }

        if (mode != 2)
            return false;

        if (instance.onCoolDown(mode) && !instance.canIgnoreCoolDown(entity, mode))
            return false;

        int castTime = 20;

        Vec3 offset = new Vec3(
                0.0,
                entity.getBbHeight() * 0.75F - entity.getEyeHeight(),
                0.0
        );

        MagicCircle.castMagicCircle(
                1.5F,
                25,
                MagicCircleVariant.LIGHT,
                entity,
                instance.getOrCreateTag(),
                0.5F,
                offset,
                instance,
                mode,
                Pair.of(0.0D, getMagiculeCost(entity, instance, mode))
        );

        if (heldTicks >= castTime) {

            Pair<Double, Double> cost = Pair.of(
                    0.0D,
                    getMagiculeCost(entity, instance, mode)
            );

            BeamProjectile.spawnLastingBeam(
                    MiscEntityTypes.SOLAR_BEAM.get(),
                    40.0F,
                    1.0F,
                    64.0F,
                    entity,
                    instance,
                    mode,
                    cost,
                    cost,
                    heldTicks
            );

            entity.level().playSound(
                    null,
                    entity.getX(),
                    entity.getY(),
                    entity.getZ(),
                    TensuraSoundEvents.CAST_LIGHT.get(),
                    SoundSource.PLAYERS,
                    0.8F,
                    0.5F
            );
        }

        return true;
    }
}


