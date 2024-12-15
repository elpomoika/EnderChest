package me.elpomoika.enderChest.database;

import me.elpomoika.enderChest.EnderChest;
import me.elpomoika.enderChest.database.impl.MysqlImpl;
import me.elpomoika.enderChest.database.impl.SqliteImpl;
import me.elpomoika.enderChest.gui.ChestGui;
import org.bukkit.configuration.file.FileConfiguration;

public class DatabaseFactory {
    private static FileConfiguration config = EnderChest.getPlugin().getConfig();
    public static Database getDatabase(String dbType) {
        // Todo доделать
        switch (dbType.toLowerCase()) {
            case "sqlite":
                return new SqliteImpl(new ChestGui());
            case "mysql":
                return new MysqlImpl(new ChestGui(), config.getString("mysql.host"), config.getInt("mysql.port"),
                        config.getString("mysql.database"),
                        config.getString("mysql.username"), config.getString("mysql.password"));
            default:
                throw new IllegalArgumentException("Неизвестное название базы данных");
        }
    }
}
