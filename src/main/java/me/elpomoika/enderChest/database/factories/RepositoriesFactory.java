package me.elpomoika.enderChest.database.factories;

import me.elpomoika.enderChest.EnderChest;
import me.elpomoika.enderChest.config.BukkitConfigProvider;
import me.elpomoika.enderChest.database.Repository;
import me.elpomoika.enderChest.config.ConfigModel;
import me.elpomoika.enderChest.database.mysql.MysqlDatabaseConnectionService;
import me.elpomoika.enderChest.database.mysql.MysqlPlayerRepository;
import me.elpomoika.enderChest.database.sqlite.SqliteDatabaseConnectionService;
import me.elpomoika.enderChest.database.sqlite.SqlitePlayerRepository;
import me.elpomoika.enderChest.gui.ChestGui;
import me.elpomoika.enderChest.gui.GuiUtils;

public class RepositoriesFactory {
    private final EnderChest plugin;
    private final BukkitConfigProvider provider;

    public RepositoriesFactory(EnderChest plugin, BukkitConfigProvider provider) {
        this.plugin = plugin;
        this.provider = provider;
    }

    public Repository getRepository(String dbType) {
        return switch (dbType.toLowerCase()) {
            case ("sqlite") ->
                    new SqlitePlayerRepository(new GuiUtils(new ChestGui()), new SqliteDatabaseConnectionService(plugin));
            case ("mysql") ->
                    new MysqlPlayerRepository(new GuiUtils(new ChestGui()), new MysqlDatabaseConnectionService(new ConfigModel(provider.getHost(),
                            provider.getPort(),
                            provider.getDatabase(),
                            provider.getUsername(),
                            provider.getPassword())));
            default -> throw new IllegalArgumentException("Incorrect database type " + dbType);
        };
    }
}
