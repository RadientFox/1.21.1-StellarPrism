package com.radientfox.stellarprism.ability.Intrinsics;

import com.github.hvnbael.trnightmare.compat.TextAnimatorCompat;
import com.radientfox.stellarprism.config.skills.StellarUniqueConfig;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.unique.BerserkerSkill;
import io.github.manasmods.tensura.ability.skill.unique.FalsifierSkill;
import io.github.manasmods.tensura.ability.skill.unique.FighterSkill;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.network.s2c.OpenIllusionItemScreenPayload;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class VulpusSkill extends Skill{

    private static final StellarUniqueConfig.Spinel CONFIG = ConfigRegistry.getConfig(StellarUniqueConfig.class).Spinel;
    public static final ResourceLocation SPINEL = ResourceLocation.fromNamespaceAndPath("stellarprism", "kitsune_illusion");

    public VulpusSkill() {
        super(SkillType.INTRINSIC);
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

    public double getDefaultAcquiringMagiculeCost() {
        return CONFIG.mpAcquirement;
    }

    public int getModes(ManasSkillInstance instance) {
        return 1;
    }


    public @Nullable ResourceLocation getSkillIcon() {
        return ResourceLocation.fromNamespaceAndPath("stellarprism", "textures/skill/intrinsic/kitsune_illusion.png");
    }

    public String getModeId(ManasSkillInstance instance, int mode) {
        String var10000;
        switch (mode) {
            case 0 -> var10000 = "kitsune_illusion.illusion";
            default -> var10000 = super.getModeId(instance, mode);
        }
        return var10000;
    }


    public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity entity, LivingEntity target, DamageSource source, Changeable<Float> amount) {
        if (source.getDirectEntity() != entity) {
            return true;
        } else if (!TensuraDamageHelper.isPhysicalAttack(source)) {
            return true;
        } else if (entity.getMainHandItem().isEmpty() && entity.getOffhandItem().isEmpty()) {
            if (instance.isToggled()) {
               // float damage = ;
               // amount.set((Float) amount.get() + damage);
                if (!instance.onCoolDown(0)) {
                    instance.addMasteryPoint(entity);
                }
            }
            return true;

        } else {
            return true;
        }
    }



    public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
        switch (mode) {
            case 0:
            if (!(entity instanceof ServerPlayer)) {
                return;
            }

            ServerPlayer player = (ServerPlayer)entity;
            NetworkManager.sendToPlayer(player, new OpenIllusionItemScreenPayload(player.getId(), this.getRegistryName()));
            player.playNotifySound((SoundEvent) TensuraSoundEvents.SPATIAL_STORAGE.get(), SoundSource.PLAYERS, 0.75F, 1.0F);

        }
    }
}
