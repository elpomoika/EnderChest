package me.elpomoika.enderChest;

import me.elpomoika.enderChest.database.Database;
import me.elpomoika.enderChest.database.DatabaseFactory;
import me.elpomoika.enderChest.listeners.OpenEChestListener;
import me.elpomoika.enderChest.listeners.onCloseEChest;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;

public final class EnderChest extends JavaPlugin {
    private Database data;
    private static EnderChest plugin;

    @Override
    public void onEnable() {
        plugin = this;
        if (!getDataFolder().exists()) getDataFolder().mkdir();


        this.getConfig().options().copyDefaults(true);
        saveConfig();

        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) saveResource("config.yml", true);

        try {
            System.out.println(this.getConfig().getString("database"));
            data = DatabaseFactory.getDatabase(this.getConfig().getString("database"));
            data.createTable();
        } catch (SQLException e) {
            Bukkit.getPluginManager().disablePlugin(this);
            System.out.println("Failed to coonect to database (main class)");
            throw new RuntimeException(e);
        }

        Bukkit.getPluginManager().registerEvents(new onCloseEChest(), this);
        Bukkit.getPluginManager().registerEvents(new OpenEChestListener(), this);
    }

    @Override
    public void onDisable() {
        data.closeConnection();
        getLogger().info("Database successfully close");
    }

    public static EnderChest getPlugin() {
        return plugin;
    }
}
