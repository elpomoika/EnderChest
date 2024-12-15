package me.elpomoika.enderChest.listeners;

import me.elpomoika.enderChest.EnderChest;
import me.elpomoika.enderChest.database.Database;
import me.elpomoika.enderChest.database.DatabaseFactory;
import me.elpomoika.enderChest.gui.ChestGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class OpenEChestListener implements Listener {
    private ChestGui chestGui = new ChestGui();
    private Database data;

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        data = DatabaseFactory.getDatabase(EnderChest.getPlugin().getConfig().getString("database"));
        Player p = event.getPlayer();

        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (!event.getClickedBlock().getType().equals(Material.ENDER_CHEST)) return;

        event.setCancelled(true);
        if (!data.playerExists(p)) {
            p.openInventory(chestGui.openGui(p));
        }

        p.openInventory(chestGui.deserializeInventory(data.getSerializedInventory(p), p));
    }
}
