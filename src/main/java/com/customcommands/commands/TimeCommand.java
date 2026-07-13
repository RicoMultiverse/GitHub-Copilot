package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class TimeCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("settime")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.argument("time", IntegerArgumentType.integer(0, 24000))
                .executes(context -> executeSetTime(
                    context.getSource(),
                    IntegerArgumentType.getInteger(context, "time")
                ))
            )
        );
    }
    
    private static int executeSetTime(ServerCommandSource source, int time) {
        try {
            source.getWorld().setTimeOfDay(time * 1000L);
            source.sendFeedback(() -> Text.literal("§aTime set to " + time), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}