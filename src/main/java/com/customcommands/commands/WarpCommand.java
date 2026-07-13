package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class WarpCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("warp")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .then(CommandManager.argument("location", StringArgumentType.string())
                    .executes(context -> executeWarp(
                        context.getSource(),
                        EntityArgumentType.getPlayer(context, "player"),
                        StringArgumentType.getString(context, "location")
                    ))
                )
            )
        );
    }
    
    private static int executeWarp(ServerCommandSource source, PlayerEntity player, String location) {
        try {
            source.sendFeedback(() -> Text.literal("§aWarped " + player.getName().getString() + " to " + location), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}