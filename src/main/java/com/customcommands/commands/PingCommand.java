package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PingCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("ping")
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .executes(context -> executePing(
                    context.getSource(),
                    EntityArgumentType.getPlayer(context, "player")
                ))
            )
        );
    }
    
    private static int executePing(ServerCommandSource source, PlayerEntity player) {
        try {
            if (player instanceof ServerPlayerEntity serverPlayer) {
                int ping = serverPlayer.networkHandler.getLatency();
                source.sendFeedback(() -> Text.literal("§aPing of " + player.getName().getString() + ": " + ping + "ms"), false);
                return 1;
            }
            return 0;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}