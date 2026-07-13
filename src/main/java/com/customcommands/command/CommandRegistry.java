package com.customcommands.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import com.customcommands.commands.*;

public class CommandRegistry {
    
    public static void registerAllCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        // Registrar todos os comandos personalizados
        TeleportCommand.register(dispatcher);
        HealCommand.register(dispatcher);
        GiveCommand.register(dispatcher);
        SpeedCommand.register(dispatcher);
        WeatherCommand.register(dispatcher);
    }
}