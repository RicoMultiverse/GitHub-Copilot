package com.customcommands.utils;

import net.minecraft.text.Text;

public class MessageUtil {
    
    public static Text success(String message) {
        return Text.literal("§a" + message);
    }
    
    public static Text info(String message) {
        return Text.literal("§b" + message);
    }
    
    public static Text warning(String message) {
        return Text.literal("§e" + message);
    }
    
    public static Text error(String message) {
        return Text.literal("§c" + message);
    }
    
    public static Text format(String message, String color) {
        return Text.literal("§" + color + message);
    }
    
    // Cores disponíveis:
    // a = Verde (success)
    // b = Azul (info)
    // c = Vermelho (error)
    // e = Amarelo (warning)
    // 6 = Ouro
    // d = Magenta
    // f = Branco
}