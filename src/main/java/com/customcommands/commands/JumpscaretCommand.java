package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

public class JumpscaretCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("jumpscare")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .executes(context -> executeJumpscare(
                    context.getSource(),
                    EntityArgumentType.getPlayer(context, "player")
                ))
            )
        );
    }
    
    private static int executeJumpscare(ServerCommandSource source, PlayerEntity player) {
        try {
            // Spawn Warden near player
            WardenEntity warden = new WardenEntity(EntityType.WARDEN, player.getWorld());
            warden.setPosition(player.getX() + 2, player.getY(), player.getZ() + 2);
            player.getWorld().spawnEntity(warden);
            
            // Play Warden sound
            player.getWorld().playSound(
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.ENTITY_WARDEN_EMERGE,
                net.minecraft.sound.SoundCategory.HOSTILE,
                1.0f,
                1.0f,
                false
            );
            
            source.sendFeedback(() -> Text.literal("§c*JUMPSCARE* Warden spawned!"), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}