package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class HomeCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("home")
            .executes(context -> executeHome(context.getSource()))
        );
    }
    
    private static int executeHome(ServerCommandSource source) {
        try {
            if (!(source.getEntity() instanceof PlayerEntity)) {
                source.sendError(Text.literal("§cOnly players can use this command!"));
                return 0;
            }
            
            PlayerEntity player = (PlayerEntity) source.getEntity();
            player.sendMessage(Text.literal("§aReturn to your home!"));
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}