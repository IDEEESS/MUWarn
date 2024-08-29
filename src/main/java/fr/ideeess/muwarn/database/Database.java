package fr.ideeess.muwarn.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Database {

    private Connection connection;

    private String host;
    private String port;
    private String user;
    private String password;
    private String dbName;
    private Logger logger;

    public Database(String host, String port, String user, String password, String dbName, Logger logger) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.dbName = dbName;
        this.logger = logger;
    }

    public void connect(){
        if (!isConnected()){
            try {
                connection = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+dbName,user,password);
                logger.info("Connection to the database " + dbName + " is ok");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void disconnect(){
        if (isConnected()){
            try {
                getConnection().close();
                logger.info("Disconnection to the database " + dbName + " is ok");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public boolean isConnected(){
        return getConnection() != null;
    }

    public Connection getConnection(){
        return connection;
    }

    public String[] getCredentials(){
        return new String[]{host,port,user,password,dbName};
    }

}
