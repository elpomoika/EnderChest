package me.elpomoika.enderChest.listeners;

import me.elpomoika.enderChest.database.EChestData;
import me.elpomoika.enderChest.gui.ChestGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class OpenEChestListener implements Listener {
    private ChestGui chestGui = new ChestGui();
    private final EChestData data;

    public OpenEChestListener(EChestData data) {
        this.data = data;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player p = event.getPlayer();

        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
            return;
        if (!event.getClickedBlock().getType().equals(Material.ENDER_CHEST))
            return;
        event.setCancelled(true);
        if (!data.playerExists(p)) {
            System.out.println(data.playerExists(p));
            p.openInventory(chestGui.openGui(p));
        }
        p.openInventory(chestGui.deserializeInventory(data.getSerializedInventory(p), p));
    }
}
