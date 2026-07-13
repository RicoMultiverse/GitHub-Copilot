package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.Enderpearl;
import net.minecraft.text.Text;

public class EnderpearlCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("enderpearl")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .executes(context -> executeEnderpearl(
                    context.getSource(),
                    EntityArgumentType.getPlayer(context, "player")
                ))
            )
        );
    }
    
    private static int executeEnderpearl(ServerCommandSource source, PlayerEntity player) {
        try {
            Enderpearl enderpearl = new Enderpearl(player.getWorld(), player);
            enderpearl.setVelocity(player, player.getPitch(), player.getYaw(), 0.0f, 1.5f, 1.0f);
            player.getWorld().spawnEntity(enderpearl);
            source.sendFeedback(() -> Text.literal("§aEnderpearl thrown!"), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}