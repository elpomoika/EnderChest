package me.elpomoika.enderChest.listeners;

import me.elpomoika.enderChest.EnderChest;
import me.elpomoika.enderChest.database.Repository;
import me.elpomoika.enderChest.database.factories.RepositoriesFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class onCloseEChest implements Listener {
    private Repository data;
    private final EnderChest plugin;
    private RepositoriesFactory repositoriesFactory;

    public onCloseEChest(EnderChest plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        repositoriesFactory = new RepositoriesFactory(plugin);
        data = repositoriesFactory.getRepository(plugin.getConfig().getString("database"));

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
