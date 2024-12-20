package me.elpomoika.enderChest.database.factories;

import me.elpomoika.enderChest.EnderChest;
import me.elpomoika.enderChest.database.Repository;
import me.elpomoika.enderChest.database.models.ConfigModel;
import me.elpomoika.enderChest.database.mysql.MysqlDatabaseConnectionService;
import me.elpomoika.enderChest.database.mysql.MysqlPlayerRepository;
import me.elpomoika.enderChest.database.sqlite.SqliteDatabaseConnectionService;
import me.elpomoika.enderChest.database.sqlite.SqlitePlayerRepository;
import me.elpomoika.enderChest.gui.ChestGui;

public class RepositoriesFactory {
    private final EnderChest plugin;

    public RepositoriesFactory(EnderChest plugin) {
        this.plugin = plugin;
    }

    public Repository getRepository(String dbType) {
        return switch (dbType.toLowerCase()) {
            case ("sqlite") ->
                    new SqlitePlayerRepository(new ChestGui(), new SqliteDatabaseConnectionService(plugin));
            case ("mysql") ->
                    new MysqlPlayerRepository(new ChestGui(), new MysqlDatabaseConnectionService(new ConfigModel(plugin)));
            default -> throw new IllegalArgumentException("Incorrect database type " + dbType);
        };
    }
}
