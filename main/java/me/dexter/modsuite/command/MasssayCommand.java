package me.dexter.modsuite.command;

import me.dexter.modsuite.command.handler.CmdArgs;
import me.dexter.modsuite.command.handler.Command;
import me.dexter.modsuite.command.handler.ICommand;
import me.dexter.modsuite.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


@Command(name = "masssay", usage = "/masssay <message>", minArgs = 1, playerOnly = true, permission = "modsuite.masssay")
public class MasssayCommand implements ICommand {

    @Override
    public void onCommand(final CmdArgs cmdArgs) {


        String[] args = cmdArgs.getArgs();
        final StringBuilder message = new StringBuilder();
        for (final String arg : args) {
            message.append(arg).append(' ');
        }

        for (Player t : Bukkit.getOnlinePlayers()) {
            t.chat(message.toString());
        }
        cmdArgs.getSender().sendMessage(Message.getString("settings.command.massay.forced").replace("%message%", message.toString()));
    }
}
