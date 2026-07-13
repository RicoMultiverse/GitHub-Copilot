package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class KickCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("kick")
            .requires(source -> source.hasPermissionLevel(3))
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .then(CommandManager.argument("reason", StringArgumentType.greedyString())
                    .executes(context -> executeKick(
                        context.getSource(),
                        EntityArgumentType.getPlayer(context, "player"),
                        StringArgumentType.getString(context, "reason")
                    ))
                )
            )
        );
    }
    
    private static int executeKick(ServerCommandSource source, PlayerEntity player, String reason) {
        try {
            source.sendFeedback(() -> Text.literal("§aPlayer " + player.getName().getString() + " kicked for: " + reason), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}