package fr.ideeess.muwarn.commands;

import fr.ideeess.muwarn.MUWarn;
import fr.ideeess.muwarn.managers.WarnManager;
import fr.ideeess.muwarn.utils.PlayersUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarnCommand implements CommandExecutor {

    MUWarn main;

    public WarnCommand(MUWarn main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("muwarn.warn")){
            sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission requise pour exécuter cette commande");
            return false;
        }

        // Vérifie que les arguments sont suffisants
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Il faut spécifier le joueur et la raison du warn");
            return false;
        }

        String playerName = args[0];

        Player player = Bukkit.getPlayer(playerName);

        if (player == null){
            sender.sendMessage(ChatColor.RED + "Joueur inconnu");
            return false;
        }

        StringBuilder stringBuilder = new StringBuilder();
        int reasonLength = args.length - 1;
        for (int i = 1; i <= reasonLength; i++){
            stringBuilder.append(args[i]).append(" ");
        }

        String reason = stringBuilder.toString();

        WarnManager warnManager = main.getWarnManager();
        warnManager.addWarn(player,reason,sender);

        return true;
    }
}
