package me.dexter.modsuite.command;

import me.dexter.modsuite.command.handler.CmdArgs;
import me.dexter.modsuite.command.handler.Command;
import me.dexter.modsuite.command.handler.ICommand;
import me.dexter.modsuite.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


@Command(name = "sudo", usage = "/sudo <player> <message>", minArgs = 1, playerOnly = true, permission = "modsuite.sudo")
public class SudoCommand implements ICommand {

    @Override
    public void onCommand(final CmdArgs cmdArgs) {
        Player player = (Player) cmdArgs.getSender();
        Player target = cmdArgs.getPlayer(0);

        if (target == null) {
            player.sendMessage("§cThat player is not online.");
            return;
        }

        if (player == target) {
            player.sendMessage(Message.getString("settings.command.sudo.cant-sudo.yourself"));
            return;
        }

        String[] args = cmdArgs.getArgs();

        final StringBuilder message = new StringBuilder();
        for (int i = 1; i < cmdArgs.getArgs().length; ++i) {

            message.append(args[i]).append(' ');
        }
        target.chat(message.toString());

        player.sendMessage(Message.getString("settings.command.sudo.forced"));
    }
}
