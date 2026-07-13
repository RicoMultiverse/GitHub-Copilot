package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class EffectsCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("giveeffects")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .executes(context -> executeEffects(
                    context.getSource(),
                    EntityArgumentType.getPlayer(context, "player")
                ))
            )
        );
    }
    
    private static int executeEffects(ServerCommandSource source, PlayerEntity player) {
        try {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 2));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 2));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 600, 2));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 600, 2));
            source.sendFeedback(() -> Text.literal("§aAll effects applied!"), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}