package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class SetHomeCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("sethome")
            .executes(context -> executeSetHome(context.getSource()))
        );
    }
    
    private static int executeSetHome(ServerCommandSource source) {
        try {
            if (!(source.getEntity() instanceof PlayerEntity)) {
                source.sendError(Text.literal("§cOnly players can use this command!"));
                return 0;
            }
            
            PlayerEntity player = (PlayerEntity) source.getEntity();
            player.sendMessage(Text.literal("§aHome set at: " + (int)player.getX() + ", " + (int)player.getY() + ", " + (int)player.getZ()));
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}