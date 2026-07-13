package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class AnnounceCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("announce")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.argument("message", StringArgumentType.greedyString())
                .executes(context -> executeAnnounce(
                    context.getSource(),
                    StringArgumentType.getString(context, "message")
                ))
            )
        );
    }
    
    private static int executeAnnounce(ServerCommandSource source, String message) {
        try {
            source.getServer().getPlayerManager().broadcast(
                Text.literal("§6[ANNOUNCEMENT] §f" + message),
                false
            );
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}