package me.elpomoika.enderChest.database;

import me.elpomoika.enderChest.gui.ChestGui;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.sql.*;

public class EChestData {

    private final Connection connection;
    private final ChestGui chestGui;

    public EChestData(String path, ChestGui chestGui) throws SQLException {
        this.chestGui = chestGui;
        String url = "jdbc:sqlite:" + path;
        connection = DriverManager.getConnection(url);

        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS players (" +
                    "username TEXT NOT NULL, " +
                    "inventory TEXT)");
        }
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public void addPlayer(Player player, Inventory inventory) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO players (username, inventory) VALUES (?, ?)")){
            preparedStatement.setString(1, player.getDisplayName());
            preparedStatement.setString(2, chestGui.serializeInventory(inventory));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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

    public void updatePlayer(Player player, Inventory inventory) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE players SET inventory = ? WHERE username = ?")){
            preparedStatement.setString(1, chestGui.serializeInventory(inventory));
            preparedStatement.setString(2, player.getDisplayName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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
