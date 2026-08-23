package com.radientfox.stellarprism.item.strengthener;

import com.radientfox.stellarprism.races.Fox.Elemental.KitsuneElement;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
/*
public class FlameStrengthen extends Item {
    private final KitsuneElement element;



    public FlameStrengthen(KitsuneElement element) {
        super(elementFor(element));
        this.element = element;
    }

    public KitsuneElement element() {
        return this.element;
    }


    private static KitsuneElement elementFor(KitsuneElement type) {
        KitsuneElement var10000;
        switch (type) {
            case WIND -> var10000 = KitsuneElement.WIND;
            case FLAME -> var10000 = KitsuneElement.FLAME;
            case WATER -> var10000 = KitsuneElement.WATER;
            case EARTH -> var10000 = KitsuneElement.EARTH;
            default -> throw new MatchException((String)null, (Throwable)null);
        }

        return var10000;
    }


    public void appendHoverText(ItemStack itemStack, Item.TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
    }

    public boolean isFoil(ItemStack pStack) {
        return pStack.isEnchanted();
    }

    public int getUseDuration(ItemStack pStack, LivingEntity entity) {
        return 16;
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if (!pPlayer.isCrouching()) {
            return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
        } else {
            ItemStack itemstack = pPlayer.getItemInHand(pHand);


            return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
        }
    }

    public @NotNull ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity entity) {
        this.applyEffect(entity, 1.0F);
        if (entity instanceof ServerPlayer player) {
            player.awardStat(Stats.ITEM_USED.get(this));
            CriteriaTriggers.CONSUME_ITEM.trigger(player, pStack);
        }



        entity.gameEvent(GameEvent.DRINK);
        return pStack;
    }

    public @NotNull InteractionResult interactLivingEntity(ItemStack pStack, Player player, LivingEntity entity, InteractionHand pHand) {
        if (entity.isAlive()) {


            entity.eat(player.level(), pStack);
            this.applyEffect(entity, 1.0F);
            entity.level().playSound(player, (Entity) entity, (SoundEvent) TensuraSoundEvents.SPIRIT_FLAME_AMBIENT, SoundSource.NEUTRAL, 1.0F, 1.0F);
            return InteractionResult.sidedSuccess(player.level().isClientSide());
        } else {
            return InteractionResult.PASS;
        }
    }

    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        return InteractionResult.PASS;
    }

    public void applyEffect(LivingEntity entity, float multiplier) {
        if (entity.isAlive()) {

            entity.level().playSound((Player)null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_HEAL.get(), SoundSource.PLAYERS, 0.5F, 1.0F);
            TensuraParticleHelper.spawnServerParticles(entity.level(), TensuraParticleUtils.getBlueWave(0.9F, entity.getBbWidth() * 2.5F, -0.5F, true), entity.getX(), entity.getY() + (double)entity.getBbHeight() * 0.33, entity.getZ());
            TensuraParticleHelper.spawnServerParticles(entity.level(), TensuraParticleUtils.getBlueWave(0.9F, entity.getBbWidth() * 2.5F, -0.5F, true), entity.getX(), entity.getY() + (double)entity.getBbHeight() * 0.66, entity.getZ());
        }
    }



}



 */