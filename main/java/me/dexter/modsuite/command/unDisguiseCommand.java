package me.dexter.modsuite.command;

import me.dexter.modsuite.api.SkinAPI;
import me.dexter.modsuite.command.handler.CmdArgs;
import me.dexter.modsuite.command.handler.Command;
import me.dexter.modsuite.command.handler.ICommand;
import me.dexter.modsuite.manager.DisguiseManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.haoshoku.nick.NickPlugin;
import xyz.haoshoku.nick.api.NickAPI;

@Command(name = "und", usage = "/undisguise", playerOnly = true)
public class unDisguiseCommand implements ICommand {

    @Override
    public void onCommand(CmdArgs cmdArgs) {
        Player player = (Player) cmdArgs.getSender();

        if (DisguiseManager.fake.containsKey(player))
        {
            NickAPI api = NickPlugin.getPlugin().getAPI();
            api.setSkin(player, (String)DisguiseManager.realName.get(player));
            api.refreshPlayer(player);

            SkinAPI.changeNick(player, (String)DisguiseManager.realName.get(player));

            DisguiseManager.fakes.remove(DisguiseManager.fake.get(player));
            DisguiseManager.fake.remove(player);
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "rank set " + (String)DisguiseManager.rankName.get(player) + " " + (String)DisguiseManager.realName.get(player));
            DisguiseManager.realName.remove(player);
            player.sendMessage("§aRemoving the disguise... \nthnx for using nxdisguise!");

        }
    }
}
