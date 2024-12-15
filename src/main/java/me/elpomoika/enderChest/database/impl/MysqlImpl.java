package me.elpomoika.enderChest.database.impl;

import me.elpomoika.enderChest.database.Database;
import me.elpomoika.enderChest.gui.ChestGui;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.sql.*;

public class MysqlImpl implements Database {
    private Connection connection;
    private final ChestGui chestGui;
    @NotNull
    private String host;
    @NotNull
    private int port;
    @NotNull
    private String database;
    @NotNull
    private String username;
    @NotNull
    private String password;

    {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
        try {
            // NullPointer потому что не может подключиться к бд
            System.out.println("We use mysql");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Cannot connect to database");
            throw new RuntimeException(e);
        }
    }

    public MysqlImpl(ChestGui chestGui, String host, int port, String database, String username, String password) {
        this.chestGui = chestGui;
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    @Override
    public void createTable() throws SQLException {
        System.out.println(this.connection != null);

        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS players (" +
                    "username TEXT NOT NULL, " +
                    "inventory TEXT)");
        }
    }

    @Override
    public void closeConnection() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
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
