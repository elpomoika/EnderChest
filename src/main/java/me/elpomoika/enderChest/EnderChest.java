package me.elpomoika.enderChest;

import me.elpomoika.enderChest.database.DatabaseConnection;
import me.elpomoika.enderChest.database.factories.DatabaseFactory;
import me.elpomoika.enderChest.listeners.OpenEChestListener;
import me.elpomoika.enderChest.listeners.onCloseEChest;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;

public final class EnderChest extends JavaPlugin {
    private DatabaseConnection data;

    @Override
    public void onEnable() {
        createConfigFile();

        DatabaseFactory databaseFactory = new DatabaseFactory(this);
        try {
            data = databaseFactory.getDatabaseConnection(getConfig().getString("database"));
            data.getConnection();
            data.createTable();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot to create table", e);
        }

        Bukkit.getPluginManager().registerEvents(new onCloseEChest(this), this);
        Bukkit.getPluginManager().registerEvents(new OpenEChestListener(this), this);
    }

    @Override
    public void onDisable() {
        data.closeConnection();
    }

    public void createConfigFile() {
        if (!getDataFolder().exists()) getDataFolder().mkdir();
        saveDefaultConfig();
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) saveResource("config.yml", false);
    }
}
