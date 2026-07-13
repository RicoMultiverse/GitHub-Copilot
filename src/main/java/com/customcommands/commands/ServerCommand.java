package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class ServerCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("serverinfo")
            .requires(source -> source.hasPermissionLevel(2))
            .executes(context -> executeServer(context.getSource()))
        );
    }
    
    private static int executeServer(ServerCommandSource source) {
        try {
            source.sendFeedback(() -> Text.literal("§bServer Information:"), false);
            source.sendFeedback(() -> Text.literal("§aVersion: 1.21.11"), false);
            source.sendFeedback(() -> Text.literal("§aModded: Yes"), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}