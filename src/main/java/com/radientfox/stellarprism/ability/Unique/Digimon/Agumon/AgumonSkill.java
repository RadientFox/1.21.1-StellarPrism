package com.radientfox.stellarprism.ability.Unique.Digimon.Agumon;

import com.radientfox.stellarprism.config.skills.StellarUniqueConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.entity.projectile.magic.FireBallProjectile;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class AgumonSkill extends Skill {
    private static final StellarUniqueConfig.AgumonSkill CONFIG = ConfigRegistry.getConfig(StellarUniqueConfig.class).AgumonSkill;
    public static final ResourceLocation AGUMON_SKILL = ResourceLocation.fromNamespaceAndPath("stellarprism", "agumon_skill");

    public AgumonSkill() {
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


    public @Nullable ResourceLocation getSkillIcon() {
        return ResourceLocation.fromNamespaceAndPath("stellarprism", "textures/skill/unique/agumon_skill.png");
    }

    public String getModeId(ManasSkillInstance instance, int mode) {
        String var10000;
        switch (mode) {
            case 0 -> var10000 = "agumon.flame";
            default -> var10000 = super.getModeId(instance, mode);
        }

        return var10000;
    }


    public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity entity, LivingEntity target, DamageSource source, Changeable<Float> amount) {
        if (source.getDirectEntity() != entity) {
            return true;
        }
        else if ((TensuraDamageHelper.isFireDamage(source))) {
          //  if ((entity.getHealth() <= (entity.getMaxHealth() * this.CONFIG.courageHealth))) {
                float dmg = (amount.get() * (float) this.CONFIG.flameBuff);
                amount.set(amount.get() + dmg);
          //  }
            return true;

        }
        else if (TensuraDamageHelper.isPhysicalAttack(source)){
            if (entity.getMainHandItem().isEmpty() && entity.getOffhandItem().isEmpty()) {
                if (instance.isToggled() && this.isInSlot(entity, instance)) {
                    float damage = (instance.isMastered(entity) ? (float) CONFIG.clawDamageMastered : (float) CONFIG.clawDamage);


                    if ((entity.getHealth() >= (entity.getMaxHealth() * this.CONFIG.courageHealth))) {
                        float dmg = ((amount.get() + damage) * (float) this.CONFIG.flameBuff);
                        amount.set((Float) amount.get());
                        if (!instance.onCoolDown(0)) {
                            instance.addMasteryPoint(entity);
                        }
                    }else {
                        amount.set((Float) amount.get() + damage);
                        if (!instance.onCoolDown(0)) {
                            instance.addMasteryPoint(entity);
                        }
                    }
                }
                return true;

            }

            if ((entity.getHealth() <= (entity.getMaxHealth() * this.CONFIG.courageHealth))) {
                float dmg = (amount.get() * (float) this.CONFIG.flameBuff);
                amount.set(amount.get() + dmg);
            }
            return true;
        }
        else {
            return true;
        }

    }


    public void onTick(ManasSkillInstance instance, LivingEntity entity) {
        if (!instance.isToggled()) return;
        AttributeInstance flamedown = entity.getAttribute(TensuraAttributes.FLAME_RESIST_DEGRADATION);

        if ((entity.getHealth() <= (entity.getMaxHealth() * this.CONFIG.courageHealth))) {
            if (flamedown != null) {
                flamedown.addOrReplacePermanentModifier(new AttributeModifier(AGUMON_SKILL, 1.0, AttributeModifier.Operation.ADD_VALUE));
            }
        }else if (flamedown != null) {
            flamedown.removeModifier(AGUMON_SKILL);
        }

    }


    public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
        boolean charged = instance.isMastered(entity);

            FireBallProjectile ball = new FireBallProjectile(entity.level(), entity);
            ball.setSize(3.0F);
            ball.setDamage((float) (charged ? CONFIG.flameDamageMastered : CONFIG.flameDamage));
            ball.setSkill(entity, instance, this, mode);
            float radius = charged ? 7 : 4;
            ball.setExplosionRadius(radius);
            ball.setHitRadius(radius);
            ball.setNoGravity(true);
            ball.setSpeed(1.5F);
            ball.setBurnTicks(100);
            ball.setImpactParticleCount(4);
            ball.setPos(entity.getEyePosition().add(0.0, -0.25, 0.0).add(entity.getLookAngle().normalize()));
            ball.shootFromRot(entity.getLookAngle());
            entity.level().addFreshEntity(ball);
            entity.level().playSound((Player)null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_FIRE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            entity.swing(InteractionHand.MAIN_HAND, true);
            instance.addMasteryPoint(entity);
            instance.setCoolDown((int) (instance.isMastered(entity) ? CONFIG.flameCooldwonMastered : CONFIG.flameCooldwon), mode);
        }

}
