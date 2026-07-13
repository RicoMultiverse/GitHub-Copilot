package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class CreateCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("create")
            .requires(source -> source.hasPermissionLevel(2))
            .executes(context -> executeCreate(context.getSource()))
        );
    }
    
    private static int executeCreate(ServerCommandSource source) {
        try {
            source.sendFeedback(() -> Text.literal("§aCreation mode enabled!"), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}