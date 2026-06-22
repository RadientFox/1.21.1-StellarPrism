//package com.radientfox.stellarprism.handler;
//
//import com.radientfox.stellarprism.Registry.main.skills.StellarUniques;
//import io.github.manasmods.tensura.ability.SkillUtils;
//import net.minecraft.ChatFormatting;
//import net.minecraft.core.component.DataComponents;
//import net.minecraft.network.chat.Component;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.sounds.SoundSource;
//import net.minecraft.world.InteractionHand;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Items;
//import net.neoforged.bus.api.SubscribeEvent;
//import net.neoforged.fml.common.EventBusSubscriber;
//import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
//
//@EventBusSubscriber(modid = "mythos")
//public class DullahanHandler {
//
//    @SubscribeEvent
//    public static void onEntityRightClick(PlayerInteractEvent.EntityInteract event) {
//
//        Player player = event.getEntity();
//
//        if (player.level().isClientSide())
//            return;
//
//        if (!SkillUtils.hasSkill(player, StellarUniques.DULLAHAN.get()))
//            return;
//
//        if (!(event.getTarget() instanceof LivingEntity target))
//            return;
//
//        if (!target.isAlive())
//            return;
//
//        float hpPercent = target.getHealth() / target.getMaxHealth();
//
//        if (hpPercent > 0.20F)
//            return;
//
//        ItemStack soulLantern = new ItemStack(Items.SOUL_LANTERN);
//
//        soulLantern.set(
//                DataComponents.CUSTOM_NAME,
//                Component.literal("Trapped Soul Lantern")
//                        .withStyle(ChatFormatting.AQUA)
//        );
//
//        soulLantern.set(
//                DataComponents.MAX_STACK_SIZE,
//                64
//        );
//
//        target.spawnAtLocation(soulLantern);
//
//        ((ServerLevel) player.level()).playSound(
//                null,
//                target.blockPosition(),
//                SoundEvents.SOUL_ESCAPE.value(),
//                SoundSource.PLAYERS,
//                1.0F,
//                0.7F
//        );
//
//        target.discard();
//
//        player.swing(InteractionHand.MAIN_HAND, true);
//
//        event.setCanceled(true);
//    }
//}