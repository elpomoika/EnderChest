package me.elpomoika.enderChest.database.sqlite;

import me.elpomoika.enderChest.EnderChest;
import me.elpomoika.enderChest.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteDatabaseConnectionService implements DatabaseConnection {
    private Connection connection;
    private String path;
    private String url;
    private final EnderChest plugin;

    public SqliteDatabaseConnectionService(EnderChest plugin) {
        this.plugin = plugin;
    }


    @Override
    public Connection getConnection() {
        path = plugin.getDataFolder().getAbsolutePath() + "/echest.db";
        url = "jdbc:sqlite:" + path;
        try {
            connection = DriverManager.getConnection(url);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to sqlite database", e);
        }
    }

    @Override
    public void createTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS players (" +
                    "username TEXT NOT NULL, " +
                    "inventory TEXT)");
        }
    }

    @Override
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
