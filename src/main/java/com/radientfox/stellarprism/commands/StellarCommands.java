package com.radientfox.stellarprism.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.radientfox.stellarprism.races.Fox.Elemental.KitsuneElement;
import com.radientfox.stellarprism.storages.KitsuneElementStorage;
import com.sun.jdi.connect.Connector;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(
        modid = "stellarprism"
)
public class StellarCommands {

    private static final SimpleCommandExceptionType PLAYER_NOT_FOUND = new SimpleCommandExceptionType(Component.literal("Player must be online."));

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("stellarprism")
                        .then(Commands.literal("KitsuneElement")
                            .then(Commands.literal("check")
                                    .then(Commands.argument("target", EntityArgument.players())
                                            .executes(context -> {
                                            return executeCheckElement(context);

                            })))
                                .then(Commands.literal("set")
                                        .then(Commands.argument("target", EntityArgument.players())
                                                .then(Commands.argument("id", ResourceLocationArgument.id())
                                                .suggests((ctx, b) -> {
                                                    return suggest(b, true);
                                                })
                                                .executes(context -> {
                                                return executeSetElement(context, String.valueOf(ResourceLocationArgument.getId(context, "id")));
                                                }
                                                )))))




        );
    }


    private static int executeCheckElement(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer target = EntityArgument.getPlayer(context, "target");


        target.displayClientMessage(Component.translatable("stellarprism.kitsune_element.check" + KitsuneElement.getElement(target)).setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), false);

        return 1;
    }


    private static int executeSetElement(CommandContext<CommandSourceStack> context, String raw) throws CommandSyntaxException {
        KitsuneElement element;
        ServerPlayer target = EntityArgument.getPlayer(context, "target");


            KitsuneElement.SetElement(target, KitsuneElement.valueOf(raw.toUpperCase(Locale.ROOT)));


        return 1;

    }




    private static CompletableFuture<Suggestions> suggest(SuggestionsBuilder builder, boolean elementType) {
        int var3;
        int var4;
        if (elementType) {
            KitsuneElement[] var2 = KitsuneElement.values();
            var3 = var2.length;

            for(var4 = 0; var4 < var3; ++var4) {
                KitsuneElement type = var2[var4];
                if (type != KitsuneElement.TIME) {
                    builder.suggest(type.name().toLowerCase(Locale.ROOT));
                }
            }
        } else {
            KitsuneElement[] var6 = KitsuneElement.values();
            var3 = var6.length;

            for(var4 = 0; var4 < var3; ++var4) {
                KitsuneElement trait = var6[var4];
                if (trait != KitsuneElement.UNIDENTIFIED) {
                    builder.suggest(trait.name().toLowerCase(Locale.ROOT));
                }
            }
        }

        return builder.buildFuture();
    }

}
