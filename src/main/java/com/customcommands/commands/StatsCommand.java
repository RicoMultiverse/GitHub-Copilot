package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class StatsCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("stats")
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .executes(context -> executeStats(
                    context.getSource(),
                    EntityArgumentType.getPlayer(context, "player")
                ))
            )
        );
    }
    
    private static int executeStats(ServerCommandSource source, PlayerEntity player) {
        try {
            String x = String.format("%.2f", player.getX());
            String y = String.format("%.2f", player.getY());
            String z = String.format("%.2f", player.getZ());
            float health = player.getHealth();
            int food = player.getHungerManager().getFoodLevel();
            
            source.sendFeedback(() -> Text.literal("§b=== Stats for " + player.getName().getString() + " ==="), false);
            source.sendFeedback(() -> Text.literal("§aLocation: X:" + x + " Y:" + y + " Z:" + z), false);
            source.sendFeedback(() -> Text.literal("§aHealth: " + health + "/20"), false);
            source.sendFeedback(() -> Text.literal("§aFood: " + food + "/20"), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}