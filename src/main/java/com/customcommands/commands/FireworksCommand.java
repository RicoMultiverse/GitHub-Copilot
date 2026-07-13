package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;

public class FireworksCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("fireworks")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .executes(context -> executeFireworks(
                    context.getSource(),
                    EntityArgumentType.getPlayer(context, "player")
                ))
            )
        );
    }
    
    private static int executeFireworks(ServerCommandSource source, PlayerEntity player) {
        try {
            for (int i = 0; i < 5; i++) {
                FireworkRocketEntity firework = new FireworkRocketEntity(player.getWorld(), player.getX(), player.getY() + 1, player.getZ(), new ItemStack(Items.FIREWORK_ROCKET));
                player.getWorld().spawnEntity(firework);
            }
            source.sendFeedback(() -> Text.literal("§aFireworks launched!"), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}