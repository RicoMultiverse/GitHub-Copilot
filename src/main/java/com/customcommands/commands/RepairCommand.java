package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class RepairCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("repair")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .executes(context -> executeRepair(
                    context.getSource(),
                    EntityArgumentType.getPlayer(context, "player")
                ))
            )
        );
    }
    
    private static int executeRepair(ServerCommandSource source, PlayerEntity player) {
        try {
            player.getMainHandStack().setDamage(0);
            source.sendFeedback(() -> Text.literal("§aItem repaired!"), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}