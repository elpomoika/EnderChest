package me.elpomoika.enderChest.gui;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class ChestGui implements InventoryHolder {
    private Inventory inventory;

    public ChestGui() {
        this.inventory = Bukkit.createInventory(this, 27, "EChest");
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
