package fr.ideeess.muwarn.managers;

import fr.ideeess.muwarn.MUWarn;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;
import java.util.logging.Logger;

public class ConfigManager {

    private final MUWarn main;
    private final Logger logger;
    private final FileConfiguration config;

    public ConfigManager(MUWarn main) {
        this.main = main;
        this.logger = main.getLogger();
        this.config = main.getConfig();
    }

    public FileConfiguration getConfig() {
        // Pas nécessaire de retenter l'accès avec Thread.sleep
        return config;
    }

    public String getHost() {
        String host = config.getString("database.host");
        if (Objects.equals(host, "yourhost")) return "null";
        return host;
    }

    public String getPort() {
        String port = config.getString("database.port");
        if (Objects.equals(port, "yourport")) return "null";
        return port;
    }

    public String getUser() {
        String user = config.getString("database.user");
        if (Objects.equals(user, "youruser")) return "null";
        return user;
    }

    public String getPassword() {
        String password = config.getString("database.password");
        if (Objects.equals(password, "yourpassword")) return "null";
        return password;
    }

    public String getDBName() {
        String dbName = config.getString("database.dbname");
        if (Objects.equals(dbName, "yourdbname")) return "null";
        return dbName;
    }

    public String[] getCredentials() {
        return new String[]{getHost(), getPort(), getUser(), getPassword(), getDBName()};
    }
}
