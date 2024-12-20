package me.elpomoika.enderChest.database.factories;

import me.elpomoika.enderChest.EnderChest;
import me.elpomoika.enderChest.config.BukkitConfigProvider;
import me.elpomoika.enderChest.config.ConfigModel;
import me.elpomoika.enderChest.database.DatabaseConnection;
import me.elpomoika.enderChest.database.mysql.MysqlDatabaseConnectionService;
import me.elpomoika.enderChest.database.sqlite.SqliteDatabaseConnectionService;

public class DatabaseFactory {
    private final EnderChest plugin;
    private final BukkitConfigProvider provider;

    public DatabaseFactory(EnderChest plugin, BukkitConfigProvider provider) {
        this.plugin = plugin;
        this.provider = provider;
    }

    public DatabaseConnection getDatabaseConnection(String dbType) {
        return switch (dbType.toLowerCase()) {
            case ("sqlite") -> new SqliteDatabaseConnectionService(plugin);
            case ("mysql") -> new MysqlDatabaseConnectionService(new ConfigModel(
                    provider.getHost(),
                    provider.getPort(),
                    provider.getDatabase(),
                    provider.getUsername(),
                    provider.getPassword()));
            default -> throw new IllegalArgumentException("Incorrect database type " + dbType);
        };
    }

}
