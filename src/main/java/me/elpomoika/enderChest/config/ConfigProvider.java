package me.elpomoika.enderChest.config;

public interface ConfigProvider {
    String getHost();
    String getPort();
    String getDatabase();
    String getUsername();
    String getPassword();
}
