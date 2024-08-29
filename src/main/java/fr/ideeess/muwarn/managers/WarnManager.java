package fr.ideeess.muwarn.managers;

import fr.ideeess.muwarn.MUWarn;
import fr.ideeess.muwarn.database.Database;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class WarnManager {

    MUWarn main;
    Database database;
    Connection connection;
    Logger logger;

    public WarnManager(MUWarn main) {
        this.main = main;
        this.database = main.getDatabase();
        this.connection = main.getDatabase().getConnection();
        this.logger = main.getLogger();
    }

    public void addWarn(Player playerWarned, String reason, CommandSender staffWarner){

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO warn(player_warned,reason,staff_who_warned) VALUES(?,?,?)");
            preparedStatement.setString(1,playerWarned.getName());
            preparedStatement.setString(2,reason);
            preparedStatement.setString(3,staffWarner.getName());

            preparedStatement.execute();

            logger.info(staffWarner.getName() + " warned " + playerWarned.getName() + " for the reason :" + reason);
            playerWarned.sendMessage(ChatColor.RED + "Vous avez été averti pour la raison suivante : " +ChatColor.GOLD + reason);
            staffWarner.sendMessage(ChatColor.GREEN + "Vous avez bien averti " + playerWarned.getName() + " pour la raison suivante : " + reason);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void delWarn(Player playerWarned, int id, CommandSender staffWarner){

    }

    public List<String[]> locker(String playerName){
        return new ArrayList<>();
    }
}
