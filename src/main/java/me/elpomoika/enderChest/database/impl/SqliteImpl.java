package me.elpomoika.enderChest.database.impl;

import me.elpomoika.enderChest.EnderChest;
import me.elpomoika.enderChest.database.Database;
import me.elpomoika.enderChest.gui.ChestGui;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.sql.*;

public class SqliteImpl implements Database {
    private final ChestGui chestGui;
    private String path = EnderChest.getPlugin().getDataFolder().getAbsolutePath() + "/echest.db";
    private String url = "jdbc:sqlite:" + path;
    private Connection connection;

    {
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public SqliteImpl(ChestGui chestGui) {
        this.chestGui = chestGui;
    }

    @Override
    public void createTable() throws SQLException {
        System.out.println("" + connection != null);

        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS players (" +
                    "username TEXT NOT NULL, " +
                    "inventory TEXT)");
        }
    }

    @Override
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addPlayer(Player player, Inventory inventory) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO players (username, inventory) VALUES (?, ?)")){
            preparedStatement.setString(1, player.getDisplayName());
            preparedStatement.setString(2, chestGui.serializeInventory(inventory));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean playerExists(Player player) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT username FROM players WHERE username = ?")){
            preparedStatement.setString(1, player.getDisplayName());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePlayer(Player player, Inventory inventory) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET inventory = ? WHERE username = ?")){
            preparedStatement.setString(1, chestGui.serializeInventory(inventory));
            preparedStatement.setString(2, player.getDisplayName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getSerializedInventory(Player player) {
        // TODO пойти нахуй
        try (PreparedStatement statement = connection.prepareStatement("SELECT inventory FROM players WHERE username = ?")) {
            statement.setString(1, player.getDisplayName());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("inventory");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
