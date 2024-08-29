package fr.ideeess.muwarn;

import fr.ideeess.muwarn.commands.DelWarnCommand;
import fr.ideeess.muwarn.commands.WarnCommand;
import fr.ideeess.muwarn.database.Database;
import fr.ideeess.muwarn.managers.ConfigManager;
import fr.ideeess.muwarn.managers.DatabaseManager;
import fr.ideeess.muwarn.managers.WarnManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class MUWarn extends JavaPlugin {
    Logger logger = getLogger();
    private Database database;
    private DatabaseManager databaseManager;
    private ConfigManager configManager;
    private WarnManager warnManager;


    @Override
    public void onEnable() {
        // Config
        saveDefaultConfig();

        //Database
        initializeDB(this);

        //Warn
        warnManager = new WarnManager(this);

        //Commands
        getCommand("warn").setExecutor(new WarnCommand(this));
        getCommand("delwarn").setExecutor(new DelWarnCommand(this));
    }

    @Override
    public void onDisable() {
        // Config
        saveConfig();

        //Database
        database.disconnect();
    }

    private void initializeDB(MUWarn main){

        configManager = new ConfigManager(main);
        String[] credentials = configManager.getCredentials();

        // Vérification des credentials
        for (String credential : credentials) {
            logger.info(credential);
            if (credential == null || credential.equals("null") || credential.isEmpty()) {
                logger.warning("Please, configure your plugin properly in config.yml");
                Bukkit.getPluginManager().disablePlugin(main);
                return;
            }
        }

        database = new Database(credentials[0],credentials[1],credentials[2],credentials[3],credentials[4],logger);
        databaseManager = new DatabaseManager(database,this);
        databaseManager.init();
    }

    public Database getDatabase() {
        return database;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public WarnManager getWarnManager() {
        return warnManager;
    }
}
