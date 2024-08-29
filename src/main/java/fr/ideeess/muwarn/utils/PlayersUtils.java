package fr.ideeess.muwarn.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayersUtils {

    public static boolean isPlayerOffline(String playerName){
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()){
            if (player.getName().equals(playerName)){
                return true;
            }
        }
        return false;
    }

}
