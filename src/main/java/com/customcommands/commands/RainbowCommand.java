package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class RainbowCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("rainbow")
            .executes(context -> executeRainbow(context.getSource()))
        );
    }
    
    private static int executeRainbow(ServerCommandSource source) {
        try {
            source.sendFeedback(() -> Text.literal("§4R§c§o§5l§6§o§a§o§3§o§2§o§b§oW"), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}