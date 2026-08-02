package com.radientfox.stellarprism.handler;

import com.radientfox.stellarprism.Registry.main.StellarEffects;
import com.radientfox.stellarprism.StellarPrism;
import com.radientfox.stellarprism.effects.BloodBlockageEffect;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
/*
@EventBusSubscriber(modid = StellarPrism.MODID)
public final class BloodBlockageHandler {


    private BloodBlockageHandler() {}

    @SubscribeEvent
    public static void onHeal(LivingHealEvent event) {
        LivingEntity entity = event.getEntity();
        MobEffectInstance effect = entity.getEffect(StellarEffects.BLOOD_BLOCKAGE);
        if (effect == null) {
            return;
        }

        int heartstones = BloodBlockageEffect.getHeartstones(effect.getAmplifier());
        if (BloodBlockageEffect.blocksHealing(entity, heartstones)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof LivingEntity living) || living.level().isClientSide()) {
            return;
        }
        if (living.tickCount % 20 != 0) {
            return;
        }

        MobEffectInstance effect = living.getEffect(StellarEffects.BLOOD_BLOCKAGE);
        if (effect == null) {
            living.getPersistentData().remove(BloodBlockageEffect.PREV_EP_TAG);
            return;
        }

        int heartstones = BloodBlockageEffect.getHeartstones(effect.getAmplifier());
        if (heartstones < 11) {
            living.getPersistentData().remove(BloodBlockageEffect.PREV_EP_TAG);
            return;
        }

        IExistence ex = TensuraStorages.getExistenceFrom(living);
        var data = living.getPersistentData();
        double current = ex.getEP();
        double previous = data.contains(BloodBlockageEffect.PREV_EP_TAG) ? data.getDouble(BloodBlockageEffect.PREV_EP_TAG) : current;

        if (current > previous) {
            double gained = current - previous;
            current = Math.max(0.0D, current - (gained * 2.0D));
            ex.setEP(current);
            ex.markDirty();
        }

        data.putDouble(BloodBlockageEffect.PREV_EP_TAG, current);
    }



}


 */