package com.customcommands;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import com.customcommands.command.CommandRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomCommandsMod implements ModInitializer {
    public static final String MOD_ID = "customcommands";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    @Override
    public void onInitialize() {
        LOGGER.info("Custom Commands Mod iniciado!");
        
        // Registrar callback para comandos
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            CommandRegistry.registerAllCommands(dispatcher);
        });
    }
}