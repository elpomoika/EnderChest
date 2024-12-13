package me.elpomoika.enderChest.database;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.sql.SQLException;

public interface EChestDataI {
    void connectDB(String path) throws SQLException;
    void closeConnection();

    void addPlayer(Player player, Inventory inventory);
    boolean playerExists(Player player);
    void updatePlayer(Player player, Inventory inventory);
    String getSerializedInventory(Player player);
}
