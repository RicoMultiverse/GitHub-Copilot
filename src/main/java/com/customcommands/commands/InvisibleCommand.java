package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class InvisibleCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("invisible")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .executes(context -> executeInvisible(
                    context.getSource(),
                    EntityArgumentType.getPlayer(context, "player")
                ))
            )
        );
    }
    
    private static int executeInvisible(ServerCommandSource source, PlayerEntity player) {
        try {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 1000000, 0));
            source.sendFeedback(() -> Text.literal("§aPlayer " + player.getName().getString() + " is now invisible"), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}