package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class PotionCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("potion")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .then(CommandManager.argument("effect", StringArgumentType.string())
                    .executes(context -> executePotion(
                        context.getSource(),
                        EntityArgumentType.getPlayer(context, "player"),
                        StringArgumentType.getString(context, "effect")
                    ))
                )
            )
        );
    }
    
    private static int executePotion(ServerCommandSource source, PlayerEntity player, String effectName) {
        try {
            StatusEffect effect = switch(effectName.toLowerCase()) {
                case "strength" -> StatusEffects.STRENGTH;
                case "speed" -> StatusEffects.SPEED;
                case "resistance" -> StatusEffects.RESISTANCE;
                case "fire_resistance" -> StatusEffects.FIRE_RESISTANCE;
                case "regeneration" -> StatusEffects.REGENERATION;
                case "jump_boost" -> StatusEffects.JUMP_BOOST;
                case "invisibility" -> StatusEffects.INVISIBILITY;
                case "haste" -> StatusEffects.HASTE;
                case "water_breathing" -> StatusEffects.WATER_BREATHING;
                case "blindness" -> StatusEffects.BLINDNESS;
                case "hunger" -> StatusEffects.HUNGER;
                case "mining_fatigue" -> StatusEffects.MINING_FATIGUE;
                case "nausea" -> StatusEffects.NAUSEA;
                case "poison" -> StatusEffects.POISON;
                case "weakness" -> StatusEffects.WEAKNESS;
                case "wither" -> StatusEffects.WITHER;
                case "slow_falling" -> StatusEffects.SLOW_FALLING;
                default -> null;
            };
            
            if (effect == null) {
                source.sendError(Text.literal("§cUnknown effect: " + effectName));
                return 0;
            }
            
            player.addStatusEffect(new StatusEffectInstance(effect, 1000000, 0));
            source.sendFeedback(() -> Text.literal("§aEffect " + effectName + " applied to " + player.getName().getString()), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}