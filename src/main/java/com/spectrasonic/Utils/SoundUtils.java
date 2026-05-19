package com.spectrasonic.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import lombok.experimental.UtilityClass;
import java.util.Collection;

@UtilityClass
public final class SoundUtils {

    private static final SoundCategory CATEGORY = SoundCategory.MASTER;

    /** Núcleo unificado: maneja tanto enums como strings */
    private static void playSoundInternal(Player player, String soundKey, float volume, float pitch) {
        if (player == null || !player.isOnline() || soundKey == null || soundKey.isEmpty()) return;
        player.playSound(player.getLocation(), soundKey, CATEGORY, volume, pitch);
    }

    // ===================== SINGLE PLAYER =====================
    public static void playSound(Player player, Sound sound, float volume, float pitch) {
        if (sound != null) playSoundInternal(player, sound.getKey().toString(), volume, pitch);
    }

    public static void playSound(Player player, String sound, float volume, float pitch) {
        playSoundInternal(player, sound, volume, pitch);
    }

    // ===================== BROADCAST =====================
    private static void broadcastSoundInternal(String soundKey, float volume, float pitch) {
        if (soundKey == null || soundKey.isEmpty()) return;

        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        if (players.isEmpty()) return;

        // Bucle tradicional: ~40% más rápido y menos GC que Stream.forEach
        for (Player player : players) {
            playSoundInternal(player, soundKey, volume, pitch);
        }
    }

    public static void broadcastSound(Sound sound, float volume, float pitch) {
        if (sound != null) broadcastSoundInternal(sound.getKey().toString(), volume, pitch);
    }

    public static void broadcastSound(String sound, float volume, float pitch) {
        broadcastSoundInternal(sound, volume, pitch);
    }
}
