package me.elpomoika.enderChest.database.factories;

import me.elpomoika.enderChest.EnderChest;
import me.elpomoika.enderChest.database.DatabaseConnection;
import me.elpomoika.enderChest.database.models.ConfigModel;
import me.elpomoika.enderChest.database.mysql.MysqlDatabaseConnectionService;
import me.elpomoika.enderChest.database.sqlite.SqliteDatabaseConnectionService;

public class DatabaseFactory {
    private final EnderChest plugin;

    public DatabaseFactory(EnderChest plugin) {
        this.plugin = plugin;
    }

    public DatabaseConnection getDatabaseConnection(String dbType) {
        return switch (dbType.toLowerCase()) {
            case ("sqlite") -> new SqliteDatabaseConnectionService(plugin);
            case ("mysql") -> new MysqlDatabaseConnectionService(new ConfigModel(plugin));
            default -> throw new IllegalArgumentException("Incorrect database type " + dbType);
        };
    }

}
