package com.customcommands.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;

public interface CustomCommand {
    void execute(ServerCommandSource source, String[] args);
    
    static void register(CommandDispatcher<ServerCommandSource> dispatcher, String name) {
        // Método auxiliar para registrar comandos
    }
}