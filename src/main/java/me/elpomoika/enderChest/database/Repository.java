package me.elpomoika.enderChest.database;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface Repository {
    void addPlayer(Player player, Inventory inventory);
    boolean playerExists(Player player);
    void updatePlayer(Player player, Inventory inventory);
    String getSerializedInventory(Player player);
}
