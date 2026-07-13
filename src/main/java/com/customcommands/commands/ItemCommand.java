package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.text.Text;

public class ItemCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("item")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .then(CommandManager.argument("itemname", StringArgumentType.string())
                    .executes(context -> executeItem(
                        context.getSource(),
                        EntityArgumentType.getPlayer(context, "player"),
                        StringArgumentType.getString(context, "itemname")
                    ))
                )
            )
        );
    }
    
    private static int executeItem(ServerCommandSource source, PlayerEntity player, String itemName) {
        try {
            var itemOptional = Registries.ITEM.getOrEmpty(new Identifier(itemName));
            if (itemOptional.isEmpty()) {
                source.sendError(Text.literal("§cItem not found: " + itemName));
                return 0;
            }
            
            ItemStack itemStack = new ItemStack(itemOptional.get(), 64);
            player.getInventory().offerOrDrop(itemStack);
            source.sendFeedback(() -> Text.literal("§aItem given!"), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}