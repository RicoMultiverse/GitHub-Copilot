package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class OnlineCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("online")
            .executes(context -> executeOnline(context.getSource()))
        );
    }
    
    private static int executeOnline(ServerCommandSource source) {
        try {
            int playerCount = source.getServer().getCurrentPlayerCount();
            int maxPlayers = source.getServer().getMaxPlayerCount();
            source.sendFeedback(() -> Text.literal("§aPlayers online: " + playerCount + "/" + maxPlayers), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}