package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import java.util.Random;

public class RandomSpawnCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("randomspawn")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .executes(context -> executeRandomSpawn(
                    context.getSource(),
                    EntityArgumentType.getPlayer(context, "player")
                ))
            )
        );
    }
    
    private static int executeRandomSpawn(ServerCommandSource source, PlayerEntity player) {
        try {
            Random random = new Random();
            int x = random.nextInt(1000) - 500;
            int z = random.nextInt(1000) - 500;
            int y = 100;
            
            player.teleport(x, y, z);
            source.sendFeedback(() -> Text.literal("§aPlayer spawned at random location!"), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}