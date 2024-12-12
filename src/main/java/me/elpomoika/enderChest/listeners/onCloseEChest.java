package me.elpomoika.enderChest.listeners;

import me.elpomoika.enderChest.database.EChestData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class onCloseEChest implements Listener {
    private final EChestData data;

    public onCloseEChest(EChestData data) {
        this.data = data;
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (event.getView().getTitle().equalsIgnoreCase("EChest")) {
            if (!data.playerExists(player)) {
                System.out.println(data.playerExists(player));
                data.addPlayer(player, event.getInventory());
                System.out.println("added");
            } else {
                data.updatePlayer(player, event.getInventory());
                System.out.println("All nice youre sexy wow bomba");
            }
        }
    }
}
