package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class RulesCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("rules")
            .executes(context -> executeRules(context.getSource()))
        );
    }
    
    private static int executeRules(ServerCommandSource source) {
        try {
            source.sendFeedback(() -> Text.literal("§b=== SERVER RULES ==="), false);
            source.sendFeedback(() -> Text.literal("§a1. Respect all players"), false);
            source.sendFeedback(() -> Text.literal("§a2. No griefing"), false);
            source.sendFeedback(() -> Text.literal("§a3. No hacking"), false);
            source.sendFeedback(() -> Text.literal("§a4. Have fun!"), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}