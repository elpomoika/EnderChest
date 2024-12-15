package me.elpomoika.enderChest.listeners;

import me.elpomoika.enderChest.EnderChest;
import me.elpomoika.enderChest.database.Database;
import me.elpomoika.enderChest.database.DatabaseFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class onCloseEChest implements Listener {
    private Database data;

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        data = DatabaseFactory.getDatabase(EnderChest.getPlugin().getConfig().getString("database"));

        Player player = (Player) event.getPlayer();
        if (event.getView().getTitle().equalsIgnoreCase("EChest")) {
            if (!data.playerExists(player)) {
                System.out.println(data.playerExists(player));
                data.addPlayer(player, event.getInventory());
            } else {
                data.updatePlayer(player, event.getInventory());
            }
        }
    }
}
