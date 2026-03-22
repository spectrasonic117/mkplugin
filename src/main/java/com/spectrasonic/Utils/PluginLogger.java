package com.spectrasonic.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import lombok.experimental.UtilityClass;

@SuppressWarnings("deprecation")
@UtilityClass
public final class PluginLogger {

    private static void log(ChatColor color, String message) {
        if (message == null)
            return;
        Bukkit.getConsoleSender().sendMessage(color + message);
    }

    public static void info(String message) {
        log(ChatColor.WHITE, message);
    }

    public static void success(String message) {
        log(ChatColor.GREEN, message);
    }

    public static void warning(String message) {
        log(ChatColor.YELLOW, message);
    }

    public static void error(String message) {
        log(ChatColor.RED, message);
    }

    public static void config(String message) {
        log(ChatColor.BLUE, message);
    }

    public static void severe(String message) {
        log(ChatColor.DARK_RED, message);
    }

    public static void severe(String message, Throwable throwable) {
        log(ChatColor.DARK_RED, message + ": " + throwable.getMessage());
    }

    public static void fine(String message) {
        log(ChatColor.GRAY, message);
    }
}