package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class SaveCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("save")
            .requires(source -> source.hasPermissionLevel(3))
            .executes(context -> executeSave(context.getSource()))
        );
    }
    
    private static int executeSave(ServerCommandSource source) {
        try {
            source.getServer().save(false, false, false);
            source.sendFeedback(() -> Text.literal("§aServer saved!"), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}