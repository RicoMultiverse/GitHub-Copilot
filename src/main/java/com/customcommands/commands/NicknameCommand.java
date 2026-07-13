package com.customcommands.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class NicknameCommand {
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("nick")
            .requires(source -> source.hasPermissionLevel(2))
            .then(CommandManager.argument("player", EntityArgumentType.player())
                .then(CommandManager.argument("nickname", StringArgumentType.greedyString())
                    .executes(context -> executeNickname(
                        context.getSource(),
                        EntityArgumentType.getPlayer(context, "player"),
                        StringArgumentType.getString(context, "nickname")
                    ))
                )
            )
        );
    }
    
    private static int executeNickname(ServerCommandSource source, PlayerEntity player, String nickname) {
        try {
            player.setCustomName(Text.literal(nickname));
            player.setCustomNameVisible(true);
            source.sendFeedback(() -> Text.literal("§aNickname set to " + nickname + " for " + player.getName().getString()), false);
            return 1;
        } catch (Exception e) {
            source.sendError(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}