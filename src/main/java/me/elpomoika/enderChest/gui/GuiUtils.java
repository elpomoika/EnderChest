package me.elpomoika.enderChest.gui;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiUtils {
    private final ChestGui chestGui;

    public GuiUtils(ChestGui chestGui) {
        this.chestGui = chestGui;
    }

    public String serializeInventory(Inventory inventory) {
        YamlConfiguration config = new YamlConfiguration();
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item != null) {
                config.set("slot." + i, item);
            }
        }
        return config.saveToString();
    }

    public Inventory deserializeInventory(String data) {
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.loadFromString(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Inventory inventory = chestGui.getInventory();
        if (config.contains("slot")) {
            for (String key : config.getConfigurationSection("slot").getKeys(false)) {
                int slot = Integer.parseInt(key);
                ItemStack item = config.getItemStack("slot." + key);
                inventory.setItem(slot, item);
            }
        }
        return inventory;
    }
}
