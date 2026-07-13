package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class BackupCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("backup")
            .requires(source -> source.hasPermissionLevel(3))
            .executes(context -> executeBackup(context.getSource()))
        );
    }
    
    private static int executeBackup(ServerCommandSource source) {
        try {
            source.sendFeedback(() -> Text.literal("§aBackup started..."), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}