package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class TeleportCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("tpjogador")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.argument("jogador", EntityArgumentType.player())
                .then(CommandManager.argument("x", DoubleArgumentType.doubleArg())
                    .then(CommandManager.argument("y", DoubleArgumentType.doubleArg())
                        .then(CommandManager.argument("z", DoubleArgumentType.doubleArg())
                            .executes(context -> executeTeleport(
                                context.getSource(),
                                EntityArgumentType.getPlayer(context, "jogador"),
                                DoubleArgumentType.getDouble(context, "x"),
                                DoubleArgumentType.getDouble(context, "y"),
                                DoubleArgumentType.getDouble(context, "z")
                            ))
                        )
                    )
                )
            )
        );
    }
    
    private static int executeTeleport(ServerCommandSource source, PlayerEntity player, double x, double y, double z) {
        try {
            player.teleport(x, y, z);
            source.sendFeedback(() -> Text.literal("§aJogador " + player.getName().getString() + " teleportado para " + x + ", " + y + ", " + z), false);
            player.sendMessage(Text.literal("§bVocê foi teleportado!"));
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cErro ao teleportar: " + e.getMessage()));
            return 0;
        }
    }
}