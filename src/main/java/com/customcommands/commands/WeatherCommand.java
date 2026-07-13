package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class WeatherCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("clima")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.literal("sol")
                .executes(context -> executeWeather(context.getSource(), "sol"))
            )
            .then(CommandManager.literal("chuva")
                .executes(context -> executeWeather(context.getSource(), "chuva"))
            )
            .then(CommandManager.literal("trovoada")
                .executes(context -> executeWeather(context.getSource(), "trovoada"))
            )
        );
    }
    
    private static int executeWeather(ServerCommandSource source, String weather) {
        try {
            if (source.getWorld() == null) {
                source.sendError(Text.literal("§cMundo não encontrado!"));
                return 0;
            }
            
            switch (weather) {
                case "sol":
                    source.getWorld().setWeather(12000, 0, false, false);
                    source.sendFeedback(() -> Text.literal("§aClima alterado para SOL!"), false);
                    break;
                case "chuva":
                    source.getWorld().setWeather(0, 6000, true, false);
                    source.sendFeedback(() -> Text.literal("§aClima alterado para CHUVA!"), false);
                    break;
                case "trovoada":
                    source.getWorld().setWeather(0, 6000, true, true);
                    source.sendFeedback(() -> Text.literal("§aClima alterado para TROVOADA!"), false);
                    break;
            }
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cErro ao alterar clima: " + e.getMessage()));
            return 0;
        }
    }
}