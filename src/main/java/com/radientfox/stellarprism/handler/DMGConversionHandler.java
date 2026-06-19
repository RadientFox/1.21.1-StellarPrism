package com.radientfox.stellarprism.handler;

import com.radientfox.stellarprism.Registry.main.StellarEffects;
import com.radientfox.stellarprism.Registry.main.skills.StellarUniques;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
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
public class DMGConversionHandler {
     public DMGConversionHandler() {
        }

        @SubscribeEvent(
                priority = EventPriority.HIGHEST,
                receiveCanceled = true
        )
        public static void onHurt(LivingIncomingDamageEvent event) {
            LivingEntity target = event.getEntity();
            DamageSource src = event.getSource();
            Entity attacker = src.getEntity();
            if (!TensuraDamageHelper.isSpatialDamage(src)) {
                if (attacker instanceof LivingEntity) {
                    LivingEntity livingAttacker = (LivingEntity)attacker;
                    if (SkillUtils.hasSkillFully(livingAttacker, (ManasSkill) StellarUniques.SPINEL_SKILL.get())) {
                        if (livingAttacker.hasEffect(StellarEffects.SPACEAURA)) {

                            if (!TensuraDamageHelper.isPhysicalAttack(src)) {
                                return;
                            }

                            Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(livingAttacker).getSkill((ManasSkill) StellarUniques.SPINEL_SKILL.get());
                            if (opt.isEmpty()) {
                                return;
                            }

                            ManasSkillInstance inst = (ManasSkillInstance) opt.get();
                            if (TensuraStorages.getAbilityFrom(livingAttacker).isAbilityInActivePreset(inst.getSkill())) {
                                float dmg = event.getAmount();
                                event.setCanceled(true);
                                ResourceKey<DamageType> type = TensuraDamageTypes.SPACE_ELEMENTAL;
                                int mode = 2;
                                DamageSource newSource = ((TensuraSkill) inst.getSkill()).createSource(inst, livingAttacker, type, mode);
                                target.hurt(newSource, dmg);
                            }
                        }
                    }
                }
            }
        }
    }


