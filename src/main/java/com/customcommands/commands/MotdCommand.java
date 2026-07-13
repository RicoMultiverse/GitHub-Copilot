package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class MotdCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("motd")
            .executes(context -> executeMotd(context.getSource()))
        );
    }
    
    private static int executeMotd(ServerCommandSource source) {
        try {
            source.sendFeedback(() -> Text.literal("§c=== WELCOME ==="), false);
            source.sendFeedback(() -> Text.literal("§bWelcome to the Custom Commands Server!"), false);
            source.sendFeedback(() -> Text.literal("§eHave fun and enjoy!"), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}