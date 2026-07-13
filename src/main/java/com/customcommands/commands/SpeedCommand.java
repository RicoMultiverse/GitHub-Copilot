package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.text.Text;

public class SpeedCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("velocidade")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.argument("jogador", EntityArgumentType.player())
                .then(CommandManager.argument("valor", FloatArgumentType.floatArg(0.1f, 10.0f))
                    .executes(context -> executeSpeed(
                        context.getSource(),
                        EntityArgumentType.getPlayer(context, "jogador"),
                        FloatArgumentType.getFloat(context, "valor")
                    ))
                )
            )
        );
    }
    
    private static int executeSpeed(ServerCommandSource source, PlayerEntity player, float speed) {
        try {
            player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
            source.sendFeedback(() -> Text.literal("§aVelocidade de " + player.getName().getString() + " alterada para " + speed), false);
            player.sendMessage(Text.literal("§bSua velocidade foi alterada para " + speed));
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cErro ao alterar velocidade: " + e.getMessage()));
            return 0;
        }
    }
}