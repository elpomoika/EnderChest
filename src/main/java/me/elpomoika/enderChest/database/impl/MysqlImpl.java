package me.elpomoika.enderChest.database.impl;

import me.elpomoika.enderChest.database.Database;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.sql.SQLException;

public class MysqlImpl implements Database {
    @Override
    public void createTable() throws SQLException {

    }

    @Override
    public void closeConnection() {

    }

    @Override
    public void addPlayer(Player player, Inventory inventory) {

    }

    @Override
    public boolean playerExists(Player player) {
        return false;
    }

    @Override
    public void updatePlayer(Player player, Inventory inventory) {

    }

    @Override
    public String getSerializedInventory(Player player) {
        return "";
    }
}
