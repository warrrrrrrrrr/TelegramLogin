package it.ivirus.telegramlogin.util;

import org.bukkit.ChatColor;

public class Util {
    public static String color(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
