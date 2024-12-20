package me.elpomoika.enderChest.listeners;

import me.elpomoika.enderChest.EnderChest;
import me.elpomoika.enderChest.config.BukkitConfigProvider;
import me.elpomoika.enderChest.database.Repository;
import me.elpomoika.enderChest.database.factories.RepositoriesFactory;
import me.elpomoika.enderChest.gui.ChestGui;
import me.elpomoika.enderChest.gui.GuiUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class OpenEChestListener implements Listener {
    private final GuiUtils guiUtils;

    private Repository data;
    private final EnderChest plugin;
    private RepositoriesFactory repositoriesFactory;

    public OpenEChestListener(GuiUtils guiUtils, EnderChest plugin) {
        this.guiUtils = guiUtils;
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        repositoriesFactory = new RepositoriesFactory(plugin, new BukkitConfigProvider(plugin));
        data = repositoriesFactory.getRepository(plugin.getConfig().getString("database"));

        Player p = event.getPlayer();

        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (!event.getClickedBlock().getType().equals(Material.ENDER_CHEST)) return;

        event.setCancelled(true);
        if (!data.playerExists(p)) {
            p.openInventory(new ChestGui().getInventory());
        }

        p.openInventory(guiUtils.deserializeInventory(data.getSerializedInventory(p)));
    }
}
