package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class GiveCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("daritem")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.argument("jogador", EntityArgumentType.player())
                .then(CommandManager.argument("item", StringArgumentType.string())
                    .then(CommandManager.argument("quantidade", IntegerArgumentType.integer(1, 64))
                        .executes(context -> executeGive(
                            context.getSource(),
                            EntityArgumentType.getPlayer(context, "jogador"),
                            StringArgumentType.getString(context, "item"),
                            IntegerArgumentType.getInteger(context, "quantidade")
                        ))
                    )
                    .executes(context -> executeGive(
                        context.getSource(),
                        EntityArgumentType.getPlayer(context, "jogador"),
                        StringArgumentType.getString(context, "item"),
                        1
                    ))
                )
            )
        );
    }
    
    private static int executeGive(ServerCommandSource source, PlayerEntity player, String itemName, int amount) {
        try {
            // Tentar encontrar o item no registro
            var itemOptional = Registries.ITEM.getOrEmpty(new Identifier(itemName));
            
            if (itemOptional.isEmpty()) {
                source.sendError(Text.literal("§cItem não encontrado: " + itemName));
                return 0;
            }
            
            ItemStack itemStack = new ItemStack(itemOptional.get(), amount);
            player.getInventory().offerOrDrop(itemStack);
            
            source.sendFeedback(() -> Text.literal("§aDoado " + amount + "x " + itemName + " para " + player.getName().getString()), false);
            player.sendMessage(Text.literal("§bVocê recebeu " + amount + "x " + itemName));
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cErro ao dar item: " + e.getMessage()));
            return 0;
        }
    }
}