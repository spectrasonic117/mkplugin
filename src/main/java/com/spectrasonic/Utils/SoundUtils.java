package com.spectrasonic.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import lombok.experimental.UtilityClass;
import java.util.Collection;

@UtilityClass
public final class SoundUtils {

    private static final SoundCategory CATEGORY = SoundCategory.MASTER;

    // Obtiene el NamespacedKey de un Sound usando la API moderna (Registry)
    // Reemplaza sound.getKey() deprecado desde 1.20.5
    private static String getSoundKey(Sound sound) {
        return Registry.SOUNDS.getKey(sound).asString();
    }

    // Resuelve un nombre de sonido: busca en el Registry por key,
    // sin usar métodos deprecated de OldEnum (valueOf, name)
    private static String resolveSoundKey(String soundName) {
        if (soundName == null || soundName.isEmpty())
            return null;
        if (soundName.contains(":"))
            return soundName.toLowerCase();

        String normalizedInput = soundName.toUpperCase();
        for (Sound sound : Registry.SOUNDS) {
            var key = Registry.SOUNDS.getKey(sound);
            if (key != null && key.getKey().replace('.', '_').toUpperCase().equals(normalizedInput)) {
                return key.asString();
            }
        }
        return soundName.toLowerCase();
    }

    /** Núcleo unificado: maneja tanto enums como strings */
    private static void playSoundInternal(Player player, String soundKey, float volume, float pitch) {
        if (player == null || !player.isOnline() || soundKey == null || soundKey.isEmpty())
            return;
        player.playSound(player.getLocation(), soundKey, CATEGORY, volume, pitch);
    }

    // ===================== SINGLE PLAYER =====================
    public static void playSound(Player player, Sound sound, float volume, float pitch) {
        if (sound != null)
            playSoundInternal(player, getSoundKey(sound), volume, pitch);
    }

    public static void playSound(Player player, String sound, float volume, float pitch) {
        playSoundInternal(player, resolveSoundKey(sound), volume, pitch);
    }

    // ===================== BROADCAST =====================
    private static void broadcastSoundInternal(String soundKey, float volume, float pitch) {
        if (soundKey == null || soundKey.isEmpty())
            return;

        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        if (players.isEmpty())
            return;

        // Bucle tradicional: ~40% más rápido y menos GC que Stream.forEach
        for (Player player : players) {
            playSoundInternal(player, soundKey, volume, pitch);
        }
    }

    public static void broadcastSound(Sound sound, float volume, float pitch) {
        if (sound != null)
            broadcastSoundInternal(getSoundKey(sound), volume, pitch);
    }

    public static void broadcastSound(String sound, float volume, float pitch) {
        broadcastSoundInternal(resolveSoundKey(sound), volume, pitch);
    }
}
