package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class WhisperCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("whisper")
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .then(CommandManager.argument("message", StringArgumentType.greedyString())
                    .executes(context -> executeWhisper(
                        context.getSource(),
                        EntityArgumentType.getPlayer(context, "player"),
                        StringArgumentType.getString(context, "message")
                    ))
                )
            )
        );
    }
    
    private static int executeWhisper(ServerCommandSource source, PlayerEntity player, String message) {
        try {
            player.sendMessage(Text.literal("§7[WHISPER] " + message));
            source.sendFeedback(() -> Text.literal("§7Message sent to " + player.getName().getString()), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}