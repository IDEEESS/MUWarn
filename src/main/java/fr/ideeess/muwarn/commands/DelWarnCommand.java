package fr.ideeess.muwarn.commands;

import fr.ideeess.muwarn.MUWarn;
import fr.ideeess.muwarn.managers.WarnManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelWarnCommand implements CommandExecutor {

    MUWarn main;

    public DelWarnCommand(MUWarn main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("muwarn.delwarn")){
            sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission requise pour exécuter cette commande");
            return false;
        }

        // Vérifie que les arguments sont suffisants
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Il faut spécifier le joueur et l'id du warn");
            return false;
        }

        String playerName = args[0];

        Player player = Bukkit.getPlayer(playerName);

        if (player == null){
            sender.sendMessage(ChatColor.RED + "Joueur inconnu");
            return false;
        }

        int id = Integer.parseInt(args[1]);

        WarnManager warnManager = main.getWarnManager();

        if (!warnManager.isIdCorrects(id,playerName)){
            sender.sendMessage(ChatColor.RED + "L'id spécifié est incorect");
            return false;
        }

        warnManager.delWarn(player,id,sender);

        return true;
    }
}
