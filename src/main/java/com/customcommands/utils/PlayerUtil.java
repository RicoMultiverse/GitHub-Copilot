package com.customcommands.utils;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class PlayerUtil {
    
    public static void teleportPlayer(PlayerEntity player, double x, double y, double z) {
        player.teleport(x, y, z);
    }
    
    public static void healPlayer(PlayerEntity player) {
        player.setHealth(player.getMaxHealth());
    }
    
    public static void giveMaxHunger(PlayerEntity player) {
        player.getHungerManager().setFoodLevel(20);
    }
    
    public static boolean isPlayerAlive(PlayerEntity player) {
        return !player.isDead();
    }
    
    public static void sendMessage(PlayerEntity player, String message) {
        player.sendMessage(Text.literal(message));
    }
    
    public static String getPlayerCoordinates(PlayerEntity player) {
        return "X: " + (int)player.getX() + " Y: " + (int)player.getY() + " Z: " + (int)player.getZ();
    }
}