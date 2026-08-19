package com.radientfox.stellarprism.ability.Unique;

import com.radientfox.stellarprism.item.BubbleWand;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class BubbleMageSkill extends Skill {
    public BubbleMageSkill() {
        super(Skill.SkillType.UNIQUE);

    }

    @Override
    public MutableComponent getSkillDescription() {
        return Component.literal("Everyone underestimates the bubble mage... Before the pop puts soap in their eyes...");
    }

    @Override
    public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
        return 150000;
    }

    @Override
    public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
        return true;
    }

    public void onTick(ManasSkillInstance instance, LivingEntity entity) {
        if (entity.level().isClientSide) return;
        if (!(entity instanceof Player player)) return;
        ItemStack mainHand = player.getMainHandItem();
    }

    @Override
    public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity attacker, LivingEntity target, DamageSource source, Changeable<Float> amount) {
        if (!(attacker instanceof Player player)) {
            return true;
        }

        ItemStack mainHand = player.getMainHandItem();

        if (!(mainHand.getItem() instanceof BubbleWand)) {
            return true;
        }

   
        TensuraMobEffect.addEffect(target, new MobEffectInstance(TensuraMobEffects.CORROSION, 100, 1, false, false, false), attacker, this);


        target.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 100, 1, false, false, false));

        return true;
    }
}