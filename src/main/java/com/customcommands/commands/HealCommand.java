package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class HealCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        // Comando para curar a si mesmo
        dispatcher.register(CommandManager.literal("curar")
            .executes(context -> executeSelfHeal(context.getSource()))
        );
        
        // Comando para curar outro jogador
        dispatcher.register(CommandManager.literal("curarjogador")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.argument("jogador", EntityArgumentType.player())
                .executes(context -> executeHeal(
                    context.getSource(),
                    EntityArgumentType.getPlayer(context, "jogador")
                ))
            )
        );
    }
    
    private static int executeSelfHeal(ServerCommandSource source) {
        if (!(source.getEntity() instanceof PlayerEntity)) {
            source.sendError(Text.literal("§cApenas jogadores podem usar este comando!"));
            return 0;
        }
        
        PlayerEntity player = (PlayerEntity) source.getEntity();
        player.setHealth(player.getMaxHealth());
        player.sendMessage(Text.literal("§aVocê foi curado!"));
        return 1;
    }
    
    private static int executeHeal(ServerCommandSource source, PlayerEntity player) {
        try {
            player.setHealth(player.getMaxHealth());
            source.sendFeedback(() -> Text.literal("§aJogador " + player.getName().getString() + " foi curado!"), false);
            player.sendMessage(Text.literal("§bVocê foi curado por um administrador!"));
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cErro ao curar: " + e.getMessage()));
            return 0;
        }
    }
}