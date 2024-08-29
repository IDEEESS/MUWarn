package fr.ideeess.muwarn.commands;

import fr.ideeess.muwarn.MUWarn;
import fr.ideeess.muwarn.managers.WarnManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LockerCommand implements CommandExecutor {

    MUWarn main;

    public LockerCommand(MUWarn main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("muwarn.locker")){
            sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission requise pour exécuter cette commande");
            return false;
        }

        // Vérifie que les arguments sont suffisants
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Il faut spécifier le joueur");
            return false;
        }

        String playerName = args[0];

        WarnManager warnManager = main.getWarnManager();

        List<String[]> locker = warnManager.getLocker(playerName);
        StringBuilder message = new StringBuilder();

        if (locker.isEmpty()){
            sender.sendMessage(ChatColor.GREEN + "Ce joueur n'a pas de casier");
            return false;
        }

        Player player = Bukkit.getPlayer(playerName);

        if (player == null){
            sender.sendMessage(ChatColor.RED + "Joueur inconnu");
            return false;
        }

        for (String[] record : locker) {
            String id = record[0];
            String reason = record[1];
            String staffWhoWarned = record[2];
            String date = record[3];

            // Formater chaque ligne comme vous le souhaitez
            message.append(ChatColor.GOLD +"Avertissement ID: " + ChatColor.RED).append(id)
                    .append(ChatColor.GOLD +"\nRaison: "+ ChatColor.RED).append(reason)
                    .append(ChatColor.GOLD +"\nDonné par: "+ ChatColor.RED).append(staffWhoWarned)
                    .append(ChatColor.GOLD +"\nDate: "+ ChatColor.RED).append(date)
                    .append("\n\n")// Ajoute un double saut de ligne pour séparer les entrées
                    .append(ChatColor.DARK_GREEN +"-----------------------------------")
                    .append("\n\n"); // Ajoute un double saut de ligne pour séparer les entrées
        }

        sender.sendMessage(ChatColor.GREEN + "Voici le casier de " + playerName +" :\n\n" + ChatColor.GOLD + message.toString());

        return true;
    }
}
