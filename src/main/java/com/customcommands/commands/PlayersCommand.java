package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class PlayersCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("players")
            .executes(context -> executePlayers(context.getSource()))
        );
    }
    
    private static int executePlayers(ServerCommandSource source) {
        try {
            var players = source.getServer().getPlayerManager().getPlayerList();
            source.sendFeedback(() -> Text.literal("§aOnline Players: " + players.size()), false);
            for (var player : players) {
                source.sendFeedback(() -> Text.literal("§b- " + player.getName().getString()), false);
            }
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}