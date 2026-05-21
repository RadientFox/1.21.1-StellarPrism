package com.radientfox.stellarprism.ability.Unique;

import com.radientfox.stellarprism.Regestrys.main.skills.StellarUniques;
import com.radientfox.stellarprism.compat.SeveranceCompat;
import com.radientfox.stellarprism.config.skills.StellarUniqueConfig;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.magic.aspectual.space.SpatialStorageMagic;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.subclass.ISpatialStorage;
import io.github.manasmods.tensura.config.ability.magic.AspectualMagicConfig;
import io.github.manasmods.tensura.entity.projectile.TensuraFlyingProjectile;
import io.github.manasmods.tensura.entity.projectile.magic.SpaceCutProjectile;
import io.github.manasmods.tensura.menu.SpatialBagMenu;
import io.github.manasmods.tensura.menu.container.SpatialStorageContainer;
import io.github.manasmods.tensura.network.s2c.OpenSpatialStorageMenuPayload;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpinelSkill extends Skill  implements ISpatialStorage {
    private static final StellarUniqueConfig.Spinel CONFIG = ConfigRegistry.getConfig(StellarUniqueConfig.class).Spinel;
    public static final ResourceLocation SPINEL = ResourceLocation.fromNamespaceAndPath("stellarprism", "spinel_skill");

    public SpinelSkill() {
        super(SkillType.UNIQUE);
    }

    public double getDefaultAcquiringMagiculeCost() {
        return CONFIG.mpAcquirement;
    }

    public int getModes(ManasSkillInstance instance) {
        return 3;
    }

    public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {

        CompoundTag tag = instance.getOrCreateTag();
        boolean auraUnlock = tag.getBoolean("auraUnlock");
        if (auraUnlock) {
            if (reverse) {
                return mode == 0 ? 2 : mode - 1;
            } else {
                return mode == 2 ? 0 : mode + 1;
            }
        }else {
            if (reverse) {
                return mode == 0 ? 1 : mode - 1;
            } else {
                return mode == 1 ? 0 : mode + 1;
            }
        }

    }

    public @Nullable ResourceLocation getSkillIcon() {
        return ResourceLocation.fromNamespaceAndPath("stellarprism", "textures/skill/unique/spinel_skill.png");
    }

    public String getModeId(ManasSkillInstance instance, int mode) {
        String var10000;
        switch (mode) {
            case 0 -> var10000 = "spinel.cutter";
            case 1 -> var10000 = "spinel.storage";
            case 2 -> var10000 = "spinel.aura";
            default -> var10000 = super.getModeId(instance, mode);
        }

        return var10000;
    }

    public double getAuraCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
        double var10000;
        switch (mode) {
            case 0 -> var10000 = CONFIG.apSpaceCutter;
            case 1 -> var10000 = CONFIG.apPocketStorage;
            default -> var10000 = 0.0;
        }

        return var10000;
    }


    public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
            switch (mode) {
                case 0:
                    entity.level().playSound((Player)null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
                    entity.swing(InteractionHand.MAIN_HAND, true);

                    SpaceCutProjectile blade = new SpaceCutProjectile(entity.level(), entity);
                    blade.setSpeed(1.5F);
                    blade.setVisible(true);
                    blade.setPiercingEntity(true);
                    blade.setPiercingBlock(true);
                    blade.setDamage((float) (instance.isMastered(entity) ? CONFIG.masterydmgSpaceCutter : CONFIG.dmgSpaceCutter));
                    blade.setSize(5.0F);
                    blade.setNoGravity(true);
                    blade.setPosDirection(entity, TensuraFlyingProjectile.PositionDirection.MIDDLE);
                    blade.shootFromRot(entity.getLookAngle());
                    entity.level().addFreshEntity(blade);


                case 1:
                    this.openSpatialStorage(entity, instance);

            }

    }

    public void openSpatialStoragePage(ServerPlayer player, LivingEntity owner, ManasSkillInstance instance, int page) {
      //  player.nextContainerCounter();
        ManasSkill skill = instance.getSkill();
        SpatialStorageContainer container = this.getSpatialStorage(instance, owner.registryAccess());
    //    NetworkManager.sendToPlayer(player, new OpenSpatialStorageMenuPayload(OpenSpatialStorageMenuPayload.StorageType.SPATIAL_BAG, player.containerCounter, container.getContainerSize(), container.getMaxStackSize(), page, owner.getId(), skill.getRegistryName()));
      //  player.containerMenu = new SpatialBagMenu(player.containerCounter, player.getInventory(), owner, container, skill, instance.isMastered(owner), page);
      //  player.initMenu(player.containerMenu);
    }

    public @NotNull SpatialStorageContainer getSpatialStorage(ManasSkillInstance instance, HolderLookup.Provider provide) {
        boolean mastered = instance.getMastery() >= (double)this.getMaxMastery();
        SpatialStorageContainer container = new SpatialStorageContainer(mastered ? CONFIG.spatialSlots + 4 : CONFIG.spatialSlots, mastered ? CONFIG.spatialStackMastered : CONFIG.spatialStack);
        container.fromTag(instance.getOrCreateTag().getList("SpatialStorage", 10), provide);
        return container;
    }

    public void onTick(ManasSkillInstance instance, LivingEntity entity) {

      //  SeveranceCompat.shouldCancelSeverance(entity, source);

    }






}
