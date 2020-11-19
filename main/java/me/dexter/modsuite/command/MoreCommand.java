package me.dexter.modsuite.command;

import me.dexter.modsuite.command.handler.CmdArgs;
import me.dexter.modsuite.command.handler.Command;
import me.dexter.modsuite.command.handler.ICommand;
import me.dexter.modsuite.utils.Message;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Command(name = "more", usage = "/more", playerOnly = true, permission = "modsuite.more")
public class MoreCommand implements ICommand {

    @Override
    public void onCommand(final CmdArgs cmdArgs) {
        Player player = (Player) cmdArgs.getSender();

        if (player.getItemInHand() == null) {
            player.sendMessage(Message.getString("settings.command.more.must-hold-item"));
            return;
        }

        player.getItemInHand().setAmount(64);
        player.updateInventory();
        player.sendMessage(Message.getString("settings.command.more.give"));
    }

}
