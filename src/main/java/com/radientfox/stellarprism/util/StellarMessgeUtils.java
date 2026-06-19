package com.radientfox.stellarprism.util;

import com.mojang.authlib.GameProfile;
import com.radientfox.stellarprism.StellarPrism;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.Iterator;
import java.util.UUID;

public class StellarMessgeUtils {

    public StellarMessgeUtils() {
    }




    @EventBusSubscriber(
            modid = "stellarprism",
            value = {Dist.CLIENT}
    )
    public static class ClientEvents {
        public ClientEvents() {
        }

        @net.neoforged.bus.api.SubscribeEvent
        public static void onPlayerJoin(net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingIn event) {
            Minecraft mc = Minecraft.getInstance();
            GameProfile playerProfile = mc.getGameProfile();
            UUID playerUUID = playerProfile.getId();
            if (playerUUID.equals(StellarPrism.RADIENTFOX)) {
                event.getPlayer().sendSystemMessage(Component.translatable("stellarprism.join.message.radient", new Object[]{playerProfile.getName()}));

            }
            else if (playerUUID.equals(StellarPrism.TERRACHARM)) {
                event.getPlayer().sendSystemMessage(Component.translatable("stellarprism.join.message.terra", new Object[]{playerProfile.getName()}));

            }
            else if (playerUUID.equals(StellarPrism.NITE)) {
                event.getPlayer().sendSystemMessage(Component.translatable("stellarprism.join.message.nite", new Object[]{playerProfile.getName()}));

            } else {
                event.getPlayer().sendSystemMessage(Component.translatable("stellarprism.join.message", new Object[]{playerProfile.getName()}));
                event.getPlayer().sendSystemMessage( Component.literal(" [ Discord Link ].").withStyle(Style.EMPTY.withColor(ChatFormatting.DARK_PURPLE).withUnderlined(true).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/GvnwfEU8v3"))));
            }


        }
    }

    @EventBusSubscriber(
            modid = "stellarprism",
            value = {Dist.CLIENT}
    )
    public static class ServerEvents {
        public ServerEvents() {
        }

        @SubscribeEvent
        public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
            ServerPlayer player = (ServerPlayer)event.getEntity();
            GameProfile playerProfile = player.getGameProfile();
            UUID playerUUID = playerProfile.getId();
            MinecraftServer server = player.server;
            Iterator var5;
            ServerPlayer onlinePlayer;
            if (playerUUID.equals(StellarPrism.RADIENTFOX)) {
                var5 = server.getPlayerList().getPlayers().iterator();

                while(var5.hasNext()) {
                    onlinePlayer = (ServerPlayer)var5.next();
                }
            }
            if (playerUUID.equals(StellarPrism.TERRACHARM)) {
                var5 = server.getPlayerList().getPlayers().iterator();

                while(var5.hasNext()) {
                    onlinePlayer = (ServerPlayer)var5.next();
                }
                event.getEntity().displayClientMessage(Component.translatable("§c <3 -Radient"), false);

            }

        }
    }





}
