package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class ExplosionCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("explosion")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .then(CommandManager.argument("power", DoubleArgumentType.doubleArg(0.1, 100.0))
                    .executes(context -> executeExplosion(
                        context.getSource(),
                        EntityArgumentType.getPlayer(context, "player"),
                        DoubleArgumentType.getDouble(context, "power")
                    ))
                )
            )
        );
    }
    
    private static int executeExplosion(ServerCommandSource source, PlayerEntity player, double power) {
        try {
            player.getWorld().createExplosion(player, player.getX(), player.getY(), player.getZ(), (float)power, false, net.minecraft.world.explosion.ExplosionSourceType.NONE);
            source.sendFeedback(() -> Text.literal("§aExplosion created with power " + power), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}