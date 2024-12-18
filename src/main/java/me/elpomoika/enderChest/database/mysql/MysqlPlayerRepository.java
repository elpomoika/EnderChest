package me.elpomoika.enderChest.database.mysql;

import me.elpomoika.enderChest.database.Repository;
import me.elpomoika.enderChest.gui.ChestGui;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlPlayerRepository implements Repository {
    private final ChestGui chestGui;
    private final MysqlDatabaseConnectionService connection;

    public MysqlPlayerRepository(ChestGui chestGui, MysqlDatabaseConnectionService connection) {
        this.chestGui = chestGui;
        this.connection = connection;
    }

    @Override
    public void addPlayer(Player player, Inventory inventory) {
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement("INSERT INTO players (username, inventory) VALUES (?, ?)")){
            preparedStatement.setString(1, player.getDisplayName());
            preparedStatement.setString(2, chestGui.serializeInventory(inventory));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean playerExists(Player player) {
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement("SELECT username FROM players WHERE username = ?")){
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
        try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement("UPDATE players SET inventory = ? WHERE username = ?")){
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
        try (PreparedStatement statement = connection.getConnection().prepareStatement("SELECT inventory FROM players WHERE username = ?")) {
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
