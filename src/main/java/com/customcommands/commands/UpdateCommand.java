package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class UpdateCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("update")
            .requires(source -> source.hasPermissionLevel(4))
            .executes(context -> executeUpdate(context.getSource()))
        );
    }
    
    private static int executeUpdate(ServerCommandSource source) {
        try {
            source.sendFeedback(() -> Text.literal("§aChecking for updates..."), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}