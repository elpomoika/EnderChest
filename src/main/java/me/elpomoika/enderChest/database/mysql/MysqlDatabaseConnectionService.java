package me.elpomoika.enderChest.database.mysql;

import me.elpomoika.enderChest.database.DatabaseConnection;
import me.elpomoika.enderChest.config.ConfigModel;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class MysqlDatabaseConnectionService implements DatabaseConnection {
    private final ConfigModel config;
    private Connection connection;

    public MysqlDatabaseConnectionService(ConfigModel config) {
        this.config = config;
    }

    @Override
    public Connection getConnection()  {
        try {
            connection = DriverManager.getConnection(config.getJdbcUrl(), config.getUsername(), config.getPassword());
            Bukkit.getLogger().info("We use Mysql");
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to database", e);
        }
    }

    @Override
    public void createTable() throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS players (" +
                    "username TEXT NOT NULL, " +
                    "inventory TEXT)");
        }
    }

    @Override
    public void closeConnection() {
        try {
            if (getConnection() != null && !getConnection().isClosed()) {
                getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
