package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class RestartCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("restart")
            .requires(source -> source.hasPermissionLevel(4))
            .executes(context -> executeRestart(context.getSource()))
        );
    }
    
    private static int executeRestart(ServerCommandSource source) {
        try {
            source.getServer().getPlayerManager().broadcast(Text.literal("§cServer is restarting!"), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}