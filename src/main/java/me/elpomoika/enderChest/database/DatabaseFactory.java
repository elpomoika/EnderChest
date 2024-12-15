package me.elpomoika.enderChest.database;

import me.elpomoika.enderChest.database.impl.MysqlImpl;
import me.elpomoika.enderChest.database.impl.SqliteImpl;
import me.elpomoika.enderChest.gui.ChestGui;

public class DatabaseFactory {
    public static Database getDatabase(String dbType) {
        // Todo доделать
        switch (dbType.toLowerCase()) {
            case "sqlite":
                return new SqliteImpl(new ChestGui());
            case "mysql":
                return new MysqlImpl();
            default:
                throw new IllegalArgumentException("Неизвестное название базыданных");
        }
    }
}
