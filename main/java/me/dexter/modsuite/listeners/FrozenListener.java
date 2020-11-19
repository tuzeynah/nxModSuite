package me.dexter.modsuite.listeners;

import me.dexter.modsuite.manager.FrozenManager;
import me.dexter.modsuite.nxModSuite;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class FrozenListener implements Listener {
    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        final Player player = e.getPlayer();
        if (FrozenManager.isFrozen(player)) {
            nxModSuite.getInstance().getServer().broadcast(ChatColor.GOLD + player.getName() + " logged out while frozen.", "core.staff");
            FrozenManager.unfreezeUUID(player);
        }
    }

    @EventHandler
    public void onItemDrop(final PlayerDropItemEvent e) {
        if (FrozenManager.isFrozen(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
}
