package me.elpomoika.enderChest;

import me.elpomoika.enderChest.database.EChestData;
import me.elpomoika.enderChest.gui.ChestGui;
import me.elpomoika.enderChest.listeners.OpenEChestListener;
import me.elpomoika.enderChest.listeners.onCloseEChest;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class EnderChest extends JavaPlugin {
    private EChestData data;
    private String path = getDataFolder().getAbsolutePath() + "/echest.db";

    @Override
    public void onEnable() {
        // Plugin startup logic
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }
            data = new EChestData(path, new ChestGui());
            Bukkit.getPluginManager().registerEvents(new onCloseEChest(new EChestData(path, new ChestGui())), this);
            Bukkit.getPluginManager().registerEvents(new OpenEChestListener(new EChestData(path, new ChestGui())), this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        try {
            data.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
