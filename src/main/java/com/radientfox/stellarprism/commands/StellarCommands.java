package com.radientfox.stellarprism.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.radientfox.stellarprism.races.Fox.Elemental.KitsuneElement;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = "stellarprism")
public class StellarCommands {

    private static final SimpleCommandExceptionType PLAYER_NOT_FOUND =
            new SimpleCommandExceptionType(
                    Component.literal("Player must be online.")
            );

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {

        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
                Commands.literal("stellarprism")
                        .then(
                                Commands.literal("KitsuneElement")

                                        // /stellarprism KitsuneElement check
                                        .then(
                                                Commands.literal("check")
                                                        .then(
                                                                Commands.argument(
                                                                                "target",
                                                                                EntityArgument.players()
                                                                        )
                                                                        .executes(StellarCommands::executeCheckElement)
                                                        )
                                        )

                                        // /stellarprism KitsuneElement set
                                        .then(
                                                Commands.literal("set")
                                                        .requires(source -> source.hasPermission(2))
                                                        .then(
                                                                Commands.argument(
                                                                                "target",
                                                                                EntityArgument.players()
                                                                        )
                                                                        .then(
                                                                                Commands.argument(
                                                                                                "id",
                                                                                                StringArgumentType.word()
                                                                                        )
                                                                                        .suggests(
                                                                                                (ctx, builder) ->
                                                                                                        suggest(builder, true)
                                                                                        )
                                                                                        .executes(
                                                                                                StellarCommands::executeSetElement
                                                                                        )
                                                                        )
                                                        )
                                        )
                        )
        );
    }

    private static int executeCheckElement(
            CommandContext<CommandSourceStack> context
    ) throws CommandSyntaxException {

        ServerPlayer target =
                EntityArgument.getPlayer(context, "target");

        KitsuneElement element =
                KitsuneElement.getElement(target);

        target.displayClientMessage(
                element.getColoredName(),
                false
        );

        return 1;
    }

    private static int executeSetElement(
            CommandContext<CommandSourceStack> context
    ) throws CommandSyntaxException {

        ServerPlayer target =
                EntityArgument.getPlayer(context, "target");

        String raw =
                StringArgumentType.getString(context, "id");

        KitsuneElement element;

        try {
            element = KitsuneElement.valueOf(
                    raw.toUpperCase(Locale.ROOT)
            );
        } catch (IllegalArgumentException e) {
            throw new SimpleCommandExceptionType(
                    Component.literal(
                            "Unknown Kitsune Element: " + raw
                    )
            ).create();
        }

        KitsuneElement.SetElement(target, element);

        context.getSource().sendSuccess(
                () -> Component.literal(
                        "Set "
                                + target.getName().getString()
                                + "'s Kitsune Element to "
                                + element.getSerializedName()
                ).withStyle(ChatFormatting.GREEN),
                true
        );

        return 1;
    }

    private static CompletableFuture<Suggestions> suggest(
            SuggestionsBuilder builder,
            boolean elementType
    ) {

        for (KitsuneElement element : KitsuneElement.values()) {
            if (element == KitsuneElement.UNIDENTIFIED) {
                continue;
            }
            builder.suggest(element.getSerializedName());
        }
        return builder.buildFuture();
    }
}