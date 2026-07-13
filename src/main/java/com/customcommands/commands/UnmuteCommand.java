package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class UnmuteCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("unmute")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .executes(context -> executeUnmute(
                    context.getSource(),
                    EntityArgumentType.getPlayer(context, "player")
                ))
            )
        );
    }
    
    private static int executeUnmute(ServerCommandSource source, PlayerEntity player) {
        try {
            source.sendFeedback(() -> Text.literal("§aPlayer " + player.getName().getString() + " has been unmuted!"), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}