package com.radientfox.stellarprism.effects;

import com.radientfox.stellarprism.Registry.main.StellarEffects;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class InterliminalityEffect extends SpiralingEffect {

    private static final ResourceLocation SUBSPACE = ResourceLocation.fromNamespaceAndPath("stellarprism", "subspace");

    private static final ResourceLocation INTERLOPER = ResourceLocation.fromNamespaceAndPath("stellarprism", "interloper");

    private static final ResourceLocation INTERLIMINALITY = ResourceLocation.fromNamespaceAndPath("stellarprism", "interliminality");

    private static final String TICK_TAG = "InterliminalityTicks";

    private static final double BASE_PERCENT = 0.005D;

    private static final double RAMP_PERCENT = 0.005D;

    private static final double MAX_PERCENT = 0.70D;

    public InterliminalityEffect() {
        super();


        NeoForge.EVENT_BUS.register(this);
    }

    private static void applyMaxHealthReduction(ServerPlayer player, double reductionPercent) {

        AttributeInstance health = player.getAttribute(Attributes.MAX_HEALTH);

        if (health == null) {
            return;
        }

        AttributeModifier oldModifier = health.getModifier(INTERLIMINALITY);

        if (oldModifier != null) {
            health.removeModifier(INTERLIMINALITY);
        }

        double baseMaxHealth = health.getValue();

        double reductionAmount = -(baseMaxHealth * reductionPercent);

        AttributeModifier modifier = new AttributeModifier(INTERLIMINALITY, reductionAmount, AttributeModifier.Operation.ADD_VALUE);

        health.addOrReplacePermanentModifier(modifier);

        if (player.getHealth() > player.getMaxHealth()) {
            player.setHealth(player.getMaxHealth());
        }
    }

    private static void removeInterliminality(ServerPlayer player) {

        player.getPersistentData().remove(TICK_TAG);

        player.getPersistentData().remove("InterliminalitySeconds");

        AttributeInstance health = player.getAttribute(Attributes.MAX_HEALTH);

        if (health != null) {

            AttributeModifier modifier = health.getModifier(INTERLIMINALITY);

            if (modifier != null) {

                health.removeModifier(INTERLIMINALITY);

                if (player.getHealth() > player.getMaxHealth()) {

                    player.setHealth(player.getMaxHealth());
                }
            }
        }

        Holder<MobEffect> effect = StellarEffects.INTERLIMINALITY;

        if (player.hasEffect(effect)) {

            player.removeEffect(effect);
        }
    }


    private static boolean hasInterloper(ServerPlayer player) {

        Skills skills = SkillAPI.getSkillsFrom(player);

        for (ManasSkillInstance skill : skills.getLearnedSkills()) {

            if (skill == null || skill.getSkill() == null) {
                continue;
            }

            ResourceLocation id = skill.getSkill().getRegistryName();

            if (INTERLOPER.equals(id)) {
                return true;
            }
        }

        return false;
    }

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent.Post event) {

        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }


        if (!player.level().dimension().location().equals(SUBSPACE)) {

            removeInterliminality(player);
            return;
        }

        if (hasInterloper(player)) {

            removeInterliminality(player);
            return;
        }

        Holder<MobEffect> effect = StellarEffects.INTERLIMINALITY;

        if (!player.hasEffect(effect)) {

            player.addEffect(new MobEffectInstance(effect, 40, 0, false, false, false));
        }

        int ticks = player.getPersistentData().getInt(TICK_TAG);

        ticks++;

        if (ticks >= 20) {

            ticks = 0;

            int seconds = player.getPersistentData().getInt("InterliminalitySeconds");

            seconds++;

            player.getPersistentData().putInt("InterliminalitySeconds", seconds);

            double reductionPercent = Math.min(BASE_PERCENT + ((seconds - 1) * RAMP_PERCENT), MAX_PERCENT);

            applyMaxHealthReduction(player, reductionPercent);
        }

        player.getPersistentData().putInt(TICK_TAG, ticks);
    }
}