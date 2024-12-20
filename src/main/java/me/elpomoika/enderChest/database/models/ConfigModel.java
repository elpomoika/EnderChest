package me.elpomoika.enderChest.database.models;

import lombok.Getter;
import me.elpomoika.enderChest.EnderChest;

@Getter
public class ConfigModel {
    // todo: database factory add config
    private final String host;
    private final String port;
    private final String database;
    private final String username;
    private final String password;
    private final EnderChest plugin;

    public ConfigModel(EnderChest plugin) {
        this.plugin = plugin;
        this.host = plugin.getConfig().getString("mysql.host");
        this.port = plugin.getConfig().getString("mysql.port");
        this.database = plugin.getConfig().getString("mysql.database");
        this.username = plugin.getConfig().getString("mysql.username");
        this.password = plugin.getConfig().getString("mysql.password");
    }


    public String getJdbcUrl() {
        return "jdbc:mysql://" + host + ":" + port + "/" + database;
    }
}
