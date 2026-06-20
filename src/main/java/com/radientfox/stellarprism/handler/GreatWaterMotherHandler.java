package com.radientfox.stellarprism.handler;

import com.radientfox.stellarprism.Registry.main.skills.StellarUniques;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = "stellarprism")
public class GreatWaterMotherHandler {

    private static final String POINTS_KEY = "CreationElementalPoints";
    private static final String LAST_POINTS_KEY = "LastDisplayedPoints";
    private static final String LAST_CHANGE_TIME_KEY = "LastPointChangeTime";
    private static final int MAX_POINTS = 1000;
    private static final int DISPLAY_TIMEOUT_TICKS = 30;


    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        if (player.level().isClientSide) return;

        SkillAPI.getSkillsFrom(player).getSkill(StellarUniques.NIMUE.get()).ifPresent(instance -> {
            CompoundTag tag = instance.getOrCreateTag();
            int currentPoints = tag.getInt(POINTS_KEY);
            int lastPoints = tag.getInt(LAST_POINTS_KEY);
            long lastChangeTime = tag.getLong(LAST_CHANGE_TIME_KEY);

            boolean inWater = player.isInWater();
            boolean inRain = player.level().isRainingAt(player.blockPosition());

            if (player.level().getGameTime() % 20 == 0) {
                if (inWater || inRain) {
                    if (currentPoints < MAX_POINTS) {
                        int gain = 5;
                        int newPoints = Math.min(MAX_POINTS, currentPoints + gain);
                        tag.putInt(POINTS_KEY, newPoints);
                        tag.putLong(LAST_CHANGE_TIME_KEY, player.level().getGameTime());
                        currentPoints = newPoints;
                    }
                }
            }

            if (currentPoints != lastPoints) {
                tag.putInt(LAST_POINTS_KEY, currentPoints);
                tag.putLong(LAST_CHANGE_TIME_KEY, player.level().getGameTime());
                lastChangeTime = player.level().getGameTime();
            }

            long timeSinceChange = player.level().getGameTime() - lastChangeTime;
            if (timeSinceChange < DISPLAY_TIMEOUT_TICKS) {
                if (player.level().getGameTime() % 10 == 0) {
                    player.displayClientMessage(
                            Component.literal("Creation Points: " + currentPoints + "/" + MAX_POINTS)
                                    .withStyle(net.minecraft.ChatFormatting.AQUA),
                            true
                    );
                }
            }
        });
    }
}
