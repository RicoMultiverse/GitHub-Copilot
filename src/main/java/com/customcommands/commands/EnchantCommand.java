package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.text.Text;

public class EnchantCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("enchant")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .then(CommandManager.argument("enchant", StringArgumentType.string())
                    .then(CommandManager.argument("level", IntegerArgumentType.integer(1, 10))
                        .executes(context -> executeEnchant(
                            context.getSource(),
                            EntityArgumentType.getPlayer(context, "player"),
                            StringArgumentType.getString(context, "enchant"),
                            IntegerArgumentType.getInteger(context, "level")
                        ))
                    )
                )
            )
        );
    }
    
    private static int executeEnchant(ServerCommandSource source, PlayerEntity player, String enchantName, int level) {
        try {
            ItemStack itemStack = player.getMainHandStack();
            var enchantOptional = Registries.ENCHANTMENT.getOrEmpty(new Identifier(enchantName));
            
            if (enchantOptional.isEmpty()) {
                source.sendError(Text.literal("§cEnchantment not found: " + enchantName));
                return 0;
            }
            
            Enchantment enchantment = enchantOptional.get();
            itemStack.addEnchantment(enchantment, level);
            source.sendFeedback(() -> Text.literal("§aEnchantment " + enchantName + " level " + level + " added"), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}