package com.radientfox.stellarprism.ability.Intrinsics;

import com.github.hvnbael.trnightmare.compat.TextAnimatorCompat;
import com.radientfox.stellarprism.config.skills.StellarIntrinsicsConfig;
import com.radientfox.stellarprism.config.skills.StellarUniqueConfig;
import com.radientfox.stellarprism.races.Fox.Elemental.ElementalFoxRace;
import com.radientfox.stellarprism.races.Fox.Elemental.KitsuneElement;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.unique.AntiSkill;
import io.github.manasmods.tensura.ability.skill.unique.FalsifierSkill;
import io.github.manasmods.tensura.ability.skill.unique.FighterSkill;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.network.s2c.OpenIllusionItemScreenPayload;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.item.TensuraMaterialItems;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public class VulpusSkill extends Skill{

    private static final StellarIntrinsicsConfig.KitsuneIllusion CONFIG = ConfigRegistry.getConfig(StellarIntrinsicsConfig.class).KitsuneIllusion;
    public static final ResourceLocation KitsuneIllusion = ResourceLocation.fromNamespaceAndPath("stellarprism", "kitsune_illusion");

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
        return 2;
    }
    @Override
    public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
        if (reverse) return (mode == 0) ? 1 : (mode - 1);

        return (mode == 1) ? 0 : (mode + 1);
    }

    public @Nullable ResourceLocation getSkillIcon() {
        return ResourceLocation.fromNamespaceAndPath("stellarprism", "textures/skill/intrinsic/kitsune_illusion.png");
    }

    public String getModeId(ManasSkillInstance instance, int mode) {
        String var10000;
        switch (mode) {
            case 0 -> var10000 = "kitsune_illusion.illusion";
            case 1 -> var10000 = "kitsune_illusion.element";
            default -> var10000 = super.getModeId(instance, mode);
        }
        return var10000;
    }


    public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
        switch (mode) {

            case 0->{
                if (!(entity instanceof ServerPlayer)) {
                    return;
                }
                ServerPlayer player = (ServerPlayer)entity;

            NetworkManager.sendToPlayer(player, new OpenIllusionItemScreenPayload(player.getId(), this.getRegistryName()));
            player.playNotifySound((SoundEvent) TensuraSoundEvents.SPATIAL_STORAGE.get(), SoundSource.PLAYERS, 0.75F, 1.0F);

                }
            case 1-> {
                if (!(entity instanceof ServerPlayer)) {
                    return;
                }
                ServerPlayer player = (ServerPlayer)entity;


                 if (KitsuneElement.getElement(entity) == KitsuneElement.TIME) {

                    if(RaceAPI.getRaceFrom(entity).getRace().isPresent() && ((ManasRaceInstance)RaceAPI.getRaceFrom(entity).getRace().get()).getRace() instanceof ElementalFoxRace) {
                        if (player.getInventory().countItem((Item) TensuraMaterialItems.ELEMENT_CORE_SPACE) == 2) {

                            KitsuneElement.SetElement(entity,KitsuneElement.SPACETIME);
                        }
                    }
                }
            }
        }
    }



    public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity entity, LivingEntity target, DamageSource source, Changeable<Float> amount) {
        if (!instance.isToggled()) {
            return true;
        } else if (source.getDirectEntity() != entity) {
            return true;
        } else if (!TensuraDamageHelper.isPhysicalAttack(source)) {
            return true;
        } else if (entity.getMainHandItem().isEmpty() && entity.getOffhandItem().isEmpty()) {
            float damage = getBounusDamage(entity);
            amount.set((Float)amount.get() + damage);
            return true;
        } else {
            return true;
        }
    }

    public int getBounusDamage(LivingEntity entity){

        if (EnergyHelper.getBaseMaxEP(entity) >= 1_000_000){
            return (int) CONFIG.sharpClaws;
        }
        else if (EnergyHelper.getBaseMaxEP(entity) >= 750_000){
            return 80;
        }
        else if (EnergyHelper.getBaseMaxEP(entity) >= 500_000){
            return 60;
        }
        else if (EnergyHelper.getBaseMaxEP(entity) >= 225_000){
            return 50;
        }
        else if (EnergyHelper.getBaseMaxEP(entity) >= 100_000){
            return 40;
        }
        else if (EnergyHelper.getBaseMaxEP(entity) >= 50_000){
            return 30;
        }
        else if (EnergyHelper.getBaseMaxEP(entity) >= 45_000){
            return 25;
        }
        else if (EnergyHelper.getBaseMaxEP(entity) >= 20_000){
            return 20;
        }
        else if (EnergyHelper.getBaseMaxEP(entity) >= 15_000){
            return 15;
        }else {
            return 1;
        }
    }

    private void assignTypeIfMissing(ManasSkillInstance instance, LivingEntity entity) {
        KitsuneElement current = readType(instance);
        if (current == null || current == KitsuneElement.UNIDENTIFIED) {

              //  KitsuneElement raceDefault = inferElementForCurrentRace(entity);
              //  if (raceDefault != null) {
              //      this.setType(instance, entity, raceDefault);
               // } else {
                   // this.setType(instance, entity, rollRandomElement(entity));
              //  }

        }
    }

   // private static KitsuneElement inferElementForCurrentRace(LivingEntity entity) {


   // }


        private void setType(ManasSkillInstance instance, LivingEntity entity, KitsuneElement type) {
        CompoundTag tag = instance.getOrCreateTag();
        String old = tag.getString("DragonFactorElementType");
        if (!type.name().equals(old)) {
            tag.putString("DragonFactorElementType", type.name());
            instance.markDirty();
            if (!entity.level().isClientSide() && entity instanceof Player) {
                Player player = (Player)entity;
               // player.displayClientMessage(Component.translatable("trnightmare.skill.dragon_factor_haki.element_assigned", new Object[]{Component.translatable(type.translationKey()).withStyle(type.color())}).withStyle(ChatFormatting.AQUA), false);
            }

        }
    }

    private static KitsuneElement readType(ManasSkillInstance instance) {
        String raw = instance.getOrCreateTag().getString("KitsuneElementType");
        if (raw != null && !raw.isBlank()) {
            try {
                return KitsuneElement.valueOf(raw.toUpperCase());
            } catch (IllegalArgumentException var3) {
                return null;
            }
        } else {
            return null;
        }
    }


/*
    private static KitsuneElement rollRandomElement(LivingEntity entity) {
        int fire = Math.max(0, CONFIG.fireWeight);
        int water = Math.max(0, c.waterWeight);
        int earth = Math.max(0, c.earthWeight);
        int wind = Math.max(0, c.windWeight);
        int space = Math.max(0, c.spaceWeight);
        int light = Math.max(0, c.lightWeight);
        int darkness = Math.max(0, c.darknessWeight);
        int ender = Math.max(0, c.enderWeight);
        int nether = Math.max(0, c.netherWeight);
        int holy = Math.max(0, c.holyWeight);
        int daemonic = Math.max(0, c.daemonicWeight);
        int total = fire + water + earth + wind + space + light + darkness + ender + nether + holy + daemonic;
        if (total <= 0) {
            return DragonFactorElement.FIRE;
        } else {
            int roll = entity.getRandom().nextInt(total);
            if ((roll -= fire) < 0) {
                return DragonFactorElement.FIRE;
            } else if ((roll -= water) < 0) {
                return DragonFactorElement.WATER;
            } else if ((roll -= earth) < 0) {
                return DragonFactorElement.EARTH;
            } else if ((roll -= wind) < 0) {
                return DragonFactorElement.WIND;
            } else if ((roll -= space) < 0) {
                return DragonFactorElement.SPACE;
            } else if ((roll -= light) < 0) {
                return DragonFactorElement.LIGHT;
            } else if ((roll -= darkness) < 0) {
                return DragonFactorElement.DARKNESS;
            } else if ((roll -= ender) < 0) {
                return DragonFactorElement.ENDER;
            } else if ((roll -= nether) < 0) {
                return DragonFactorElement.NETHER;
            } else {
                return roll - holy < 0 ? DragonFactorElement.HOLY : DragonFactorElement.DAEMONIC;
            }
        }
    }

 */

    }
