package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameMode;
import net.minecraft.text.Text;

public class GamemodeCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("gamemode")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .then(CommandManager.argument("mode", StringArgumentType.string())
                    .executes(context -> executeGamemode(
                        context.getSource(),
                        EntityArgumentType.getPlayer(context, "player"),
                        StringArgumentType.getString(context, "mode")
                    ))
                )
            )
        );
    }
    
    private static int executeGamemode(ServerCommandSource source, PlayerEntity player, String mode) {
        try {
            GameMode gameMode = switch(mode.toLowerCase()) {
                case "survival" -> GameMode.SURVIVAL;
                case "creative" -> GameMode.CREATIVE;
                case "adventure" -> GameMode.ADVENTURE;
                case "spectator" -> GameMode.SPECTATOR;
                default -> null;
            };
            
            if (gameMode == null) {
                source.sendError(Text.literal("§cInvalid gamemode! Use: survival, creative, adventure, spectator"));
                return 0;
            }
            
            player.changeGameMode(gameMode);
            source.sendFeedback(() -> Text.literal("§aGamemode changed to " + mode + " for " + player.getName().getString()), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}