package com.radientfox.stellarprism.ability.Unique;

import com.github.hvnbael.trnightmare.util.damage.VoidDamageHelper;
import com.radientfox.stellarprism.Registry.main.StellarEffects;
import com.radientfox.stellarprism.Registry.main.skills.StellarExtras;
import com.radientfox.stellarprism.config.skills.StellarUniqueConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.entity.template.subclass.ISubordinate;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class VoidPriestess extends Skill {


    private static final StellarUniqueConfig.VoidPriestess CONFIG = ConfigRegistry.getConfig(StellarUniqueConfig.class).VoidPriestess;
    public static final ResourceLocation SPINEL = ResourceLocation.fromNamespaceAndPath("stellarprism", "void_priestess_skill");

    public VoidPriestess() {
        super(SkillType.UNIQUE);
    }

    public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
        return true;
    }

    public int getModes(ManasSkillInstance instance) {
        return 1;
    }


    public @Nullable ResourceLocation getSkillIcon() {
        return ResourceLocation.fromNamespaceAndPath("stellarprism", "textures/skill/unique/void_priestess_skill.png");
    }

    public String getModeId(ManasSkillInstance instance, int mode) {
        String var10000;
        switch (mode) {
            case 0 -> var10000 = "void_priestess.charm";
            default -> var10000 = super.getModeId(instance, mode);
        }

        return var10000;
    }
    public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
        return instance.getMastery() >= 0.0;
    }

    public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity attacker, LivingEntity target, DamageSource source, Changeable<Float> amount) {
        if (source.getDirectEntity() != attacker) {
            return true;
        } else if (!VoidDamageHelper.isVoidDamage(source)) {
            return true;
        }else if (instance.isToggled()){
            return true;
        }
        else {

                if (attacker.hasEffect(StellarEffects.VOID_SUBORDINATE)) {
                    MobEffectInstance current = attacker.getEffect(StellarEffects.VOID_SUBORDINATE);
                    assert current != null;
                    int effectLvl = (current.getAmplifier() + 1);
                    float damage = (float) ((instance.isMastered(attacker) ? CONFIG.increaseSelfMastered : 10) * effectLvl);
                    target.invulnerableTime = 0;
                    VoidDamageHelper.dealVoidDamage(target,attacker,damage);
                }
                return true;

        }

    }


        public void onTick(ManasSkillInstance instance, LivingEntity entity) {
        Level var6 = entity.level();
        if (var6 instanceof ServerLevel serverLevel) {
            if (instance.isToggled()) {

                float radius = 5000.0F;
                List<LivingEntity> list = entity.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius), (living) -> {
                    return !living.is(entity) && living.isAlive() && living.isAlliedTo(entity);
                });
                Iterator var10 = list.iterator();
                while (var10.hasNext()) {
                    Level var2 = entity.level();

                    LivingEntity subordinate = (LivingEntity) var10.next();
                    if (var2 instanceof ServerLevel level) {


                        int quantity = list.size();

                        if (subordinate != entity) {
                            subordinate.addEffect(new MobEffectInstance(StellarEffects.VOID_SUBORDINATE, 100, quantity - 1, false, false));

                            if (!SkillUtils.hasSkill(subordinate, (ManasSkill) StellarExtras.VOID_SUBORDINATE_SKILL.get())) {
                                // SkillHelper.learnSkill(subordinate, (ManasSkill) StellarExtras.VOID_SUBORDINATE_SKILL);
                            }
                        }
                        entity.addEffect(new MobEffectInstance(StellarEffects.VOID_SUBORDINATE, 100, quantity - 1, false, true));

                    }
                }
            }
        }
    }


    public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
            Level level = entity.level();
            UUID uuid = entity.getUUID();
            LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, 15, false);

        if (target != null && target.isAlive()) {
                if (target instanceof Player) {
                    Player player = (Player) target;
                    if (player.getAbilities().invulnerable) {
                        return;
                    }

                }
                IExistence existence = TensuraStorages.getExistenceFrom(target);

                double targetEP = TensuraStorages.getExistenceFrom(target).getEP();

                if (targetEP > TensuraStorages.getExistenceFrom(entity).getEP()) {
                    if (entity instanceof Player) {
                        Player player = (Player) entity;
                        player.displayClientMessage(Component.translatable("tensura.targeting.ep_not_meet").withStyle(ChatFormatting.RED), false);
                    }
                } else if (!EnergyHelper.isOutOfEnergy(entity, 0.0, CONFIG.charmCost)) {
                    if (!((TensuraEntityEvents.ForceTameEvent)TensuraEntityEvents.FORCE_TAME_EVENT.invoker()).tame(target, entity, CONFIG.charmTicks != -1).isFalse()) {
                        if (CONFIG.charmTicks != -1) {
                            MobEffectInstance mindControl = new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MIND_CONTROL), (int) CONFIG.charmTicks, 0, false, false, false);
                            TensuraMobEffect.addEffect(target, mindControl, entity, this, mode);
                            if (!target.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.MIND_CONTROL))) {
                                return;
                            }
                        }

                        existence.setTemporaryOwner(uuid);
                        if (target instanceof Mob) {
                            Mob mob = (Mob)target;
                            SubordinateHelper.removeTarget(mob);
                        }

                        if (target instanceof ISubordinate) {
                            ISubordinate subordinate = (ISubordinate)target;
                            if (entity instanceof Player) {
                                Player player = (Player)entity;
                                subordinate.tame(player);
                            }
                        }
                        instance.setCoolDown((int) CONFIG.charmCooldown, mode);
                        existence.markDirty();
                        instance.addMasteryPoint(entity);
                        entity.swing(InteractionHand.MAIN_HAND, true);
                        level.playSound((Player)null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.MUSIC_REQUIEM, SoundSource.PLAYERS, 1.0F, 1.0F);
                        TensuraParticleHelper.addServerParticlesAroundSelf(target, ParticleTypes.NOTE);
                        TensuraParticleHelper.addServerParticlesAroundSelf((Entity) target, (ParticleOptions) TensuraParticleTypes.BLACK_LIGHTNING_SPARK, 2.0);

                    }
                }
        }
    }

}
