package fr.ideeess.muwarn.managers;

import fr.ideeess.muwarn.MUWarn;
import fr.ideeess.muwarn.database.Database;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.Objects;
import java.util.logging.Logger;

public class DatabaseManager {

    Database database;
    MUWarn main;

    public DatabaseManager(Database database, MUWarn main) {
        this.database = database;
        this.main = main;
    }

    public void init(){

        try {
            database.connect();
            if (!isTableExists()) createTable();
        }catch (Exception e){
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(main);
        }
    }

    public void createTable() {

        Connection connection = database.getConnection();
        Logger logger = main.getLogger();

        if (!database.isConnected()) {
            logger.warning("Database is not connected");
            return;
        }

        try {
            PreparedStatement statement = connection.prepareStatement("""
                    CREATE TABLE 'warn' (
                      'id' bigint unsigned NOT NULL AUTO_INCREMENT,
                      'player_warned' varchar(255) DEFAULT NULL,
                      'reason' text,
                      'staff_who_warned' varchar(255) DEFAULT NULL,
                      'date' timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                      PRIMARY KEY ('id'),
                      UNIQUE KEY 'id' ('id')
                    );""");
            statement.execute();
            logger.info("Table created successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean isTableExists(){

        Connection connection = database.getConnection();
        Logger logger = main.getLogger();

        if (!database.isConnected()) {
            logger.warning("Database is not connected");
            return false;
        }

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) "
                    + "FROM information_schema.tables "
                    + "WHERE table_name = ?"
                    + "LIMIT 1;");
            preparedStatement.setString(1, "warn");

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            int getTable = resultSet.getInt(1);

            if (getTable != 0 ){
                logger.info("Table exists");
            }else{
                logger.info("Table doesn't exist");
            }


            return resultSet.getInt(1) != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
