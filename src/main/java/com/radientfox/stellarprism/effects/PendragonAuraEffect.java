package com.radientfox.stellarprism.effects;

import io.github.manasmods.manascore.attribute.api.ManasCoreAttributeUtils;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.effect.template.DamageAction;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.enchantment.SlottingHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import java.awt.*;

public class PendragonAuraEffect extends TensuraMobEffect implements DamageAction {

    public static final ResourceLocation PENDRAGON_AURA =
            ResourceLocation.fromNamespaceAndPath("stellarprism", "pendragon_aura");

    public PendragonAuraEffect() {
        super(MobEffectCategory.BENEFICIAL, new Color(255, 240, 110).getRGB());

        this.addAttributeModifier(
                TensuraAttributes.PHYSICAL_RESIST_DEGRADATION,
                PENDRAGON_AURA,
                1.0D,
                Operation.ADD_VALUE
        );
    }

    @Override
    public boolean onPlayerAttack(Player attacker, Entity target) {

        MobEffectInstance aura = attacker.getEffect(
                com.radientfox.stellarprism.Registry.main.StellarEffects.PENDRAGON_AURA
        );

        if (aura == null)
            return true;

        ItemStack stack = attacker.getMainHandItem();

        if (SlottingHelper.getContentSize(stack) > 0)
            return true;

        DamageSource source = getAuraSource(attacker, aura);

        float damage = ManasCoreAttributeUtils.getAttackDamage(attacker);

        Level level = attacker.level();

        if (level instanceof ServerLevel server) {
            damage = EnchantmentHelper.modifyDamage(
                    server,
                    attacker.getOffhandItem(),
                    target,
                    source,
                    damage
            );
        }

        target.hurt(source, damage);

        target.invulnerableTime = 0;

        return true;
    }

    private DamageSource getAuraSource(LivingEntity attacker, MobEffectInstance aura) {

        boolean light = attacker.getPersistentData().getBoolean("PendragonLightMode");

        ResourceKey<DamageType> type =
                light
                        ? TensuraDamageTypes.LIGHT_ELEMENTAL
                        : TensuraDamageTypes.HOLY_DAMAGE;

        DamageSource source =
                TensuraDamageHelper.getAbilityDamageSource(
                        type,
                        attacker,
                        null
                );

        return source;
    }
}
