package com.customcommands.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import com.customcommands.commands.*;

public class CommandRegistry {
    
    public static void registerAllCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        // Original Commands
        TeleportCommand.register(dispatcher);
        HealCommand.register(dispatcher);
        GiveCommand.register(dispatcher);
        SpeedCommand.register(dispatcher);
        WeatherCommand.register(dispatcher);
        
        // New Commands (50+)
        FlyCommand.register(dispatcher);
        GodModeCommand.register(dispatcher);
        NightVisionCommand.register(dispatcher);
        GamemodeCommand.register(dispatcher);
        TimeCommand.register(dispatcher);
        FeedCommand.register(dispatcher);
        KillCommand.register(dispatcher);
        SetHomeCommand.register(dispatcher);
        HomeCommand.register(dispatcher);
        NicknameCommand.register(dispatcher);
        FireworksCommand.register(dispatcher);
        LightningCommand.register(dispatcher);
        InvisibleCommand.register(dispatcher);
        PotionCommand.register(dispatcher);
        EffectsCommand.register(dispatcher);
        ClearInventoryCommand.register(dispatcher);
        MoneyCommand.register(dispatcher);
        WarpCommand.register(dispatcher);
        EnderpearlCommand.register(dispatcher);
        ShrinkCommand.register(dispatcher);
        GiantCommand.register(dispatcher);
        NoClipCommand.register(dispatcher);
        RainbowCommand.register(dispatcher);
        ExplosionCommand.register(dispatcher);
        FreezeCommand.register(dispatcher);
        BlockCommand.register(dispatcher);
        CreateCommand.register(dispatcher);
        TrollCommand.register(dispatcher);
        MuteCommand.register(dispatcher);
        UnmuteCommand.register(dispatcher);
        KickCommand.register(dispatcher);
        BanCommand.register(dispatcher);
        WarningCommand.register(dispatcher);
        WhisperCommand.register(dispatcher);
        AnnounceCommand.register(dispatcher);
        RulesCommand.register(dispatcher);
        StatsCommand.register(dispatcher);
        PingCommand.register(dispatcher);
        OnlineCommand.register(dispatcher);
        PlayersCommand.register(dispatcher);
        MotdCommand.register(dispatcher);
        ServerCommand.register(dispatcher);
        RestartCommand.register(dispatcher);
        SaveCommand.register(dispatcher);
        BackupCommand.register(dispatcher);
        UpdateCommand.register(dispatcher);
        RepairCommand.register(dispatcher);
        EnchantCommand.register(dispatcher);
        ItemCommand.register(dispatcher);
        RandomSpawnCommand.register(dispatcher);
        
        // Jumpscare Command
        JumpscaretCommand.register(dispatcher);
    }
}