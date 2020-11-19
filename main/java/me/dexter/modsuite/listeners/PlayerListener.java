package me.dexter.modsuite.listeners;

import me.dexter.modsuite.nxSuiteLang;
import me.dexter.modsuite.utils.CC;
import me.dexter.modsuite.utils.Message;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (Message.getBoolean("settings.join-message.enabled")) {
            for (String joinMessage : Message.getStringList("settings.join-message.message")) {
                nxSuiteLang lol = new nxSuiteLang(player);
                player.sendMessage(CC.translate(joinMessage.replace("%player%", lol.getColoredName())
                ));
            }
        }

        player.setPlayerListName(new nxSuiteLang(player).getColoredName());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        player.setPlayerListName(new nxSuiteLang(player).getColoredName());
    }
}
