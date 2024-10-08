package fr.ideeess.muwarn.managers;

import fr.ideeess.muwarn.MUWarn;
import fr.ideeess.muwarn.database.Database;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.*;
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
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM warn WHERE id = ? AND player_warned = ?");
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2, playerWarned.getName());

            preparedStatement.execute();

            logger.info(staffWarner.getName() + " unwarned " + playerWarned.getName());
            staffWarner.sendMessage(ChatColor.GREEN + "Vous avez bien unwarn " + playerWarned.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isIdCorrects(int id, String playerName){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM warn WHERE id = ? AND player_warned = ?");
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,playerName);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return resultSet.getInt(1) != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String[]> getLocker(String playerName){

        ArrayList<String[]> locker = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM warn WHERE player_warned = ?");
            preparedStatement.setString(1,playerName);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String reason = resultSet.getString("reason");
                String staff_who_warned = resultSet.getString("staff_who_warned");
                Timestamp date = resultSet.getTimestamp("date");

                locker.add(new String[]{String.valueOf(id),reason,staff_who_warned, date.toString()});
            }
            return locker;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
