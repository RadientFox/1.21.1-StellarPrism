package com.radientfox.stellarprism.handler;

import com.github.hvnbael.trnightmare.util.damage.VoidDamageHelper;
import com.radientfox.stellarprism.Registry.main.skills.StellarUniques;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.Optional;


@EventBusSubscriber(
        modid = "stellarprism"
)
public class VoidDMGConversionHelper {
     public VoidDMGConversionHelper() {
        }

        @SubscribeEvent(
                priority = EventPriority.HIGHEST,
                receiveCanceled = true
        )
        public static void onHurt(LivingIncomingDamageEvent event) {
            LivingEntity target = event.getEntity();
            DamageSource src = event.getSource();
            Entity attacker = src.getEntity();

            if (!VoidDamageHelper.isVoidDamage(src)) {
                if (attacker instanceof LivingEntity) {
                    LivingEntity livingAttacker = (LivingEntity)attacker;
                    if (!SkillUtils.hasSkillFully(livingAttacker, (ManasSkill)StellarUniques.VOID_PRIESTESS_SKILL.get())) {
                        return;
                    }

                    if (!TensuraDamageHelper.isPhysicalAttack(src) && !TensuraDamageHelper.isSoundDamage(src)) {
                        return;
                    }

                    Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(livingAttacker).getSkill((ManasSkill)StellarUniques.VOID_PRIESTESS_SKILL.get());
                    if (opt.isEmpty()) {
                        return;
                    }


                    ManasSkillInstance inst = (ManasSkillInstance)opt.get();
                        float dmg = event.getAmount();
                        event.setCanceled(true);
                        VoidDamageHelper.dealVoidDamage(target, (LivingEntity) attacker, dmg);
                }

            }

        }
    }


