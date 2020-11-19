package me.dexter.modsuite.command.handler;

import me.dexter.modsuite.command.*;
import org.bukkit.plugin.java.*;
import me.dexter.modsuite.command.handler.*;
import java.lang.annotation.*;
import org.bukkit.command.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import java.util.*;

public class CommandHandler implements CommandExecutor
{
    private List<ICommand> commands;
    private JavaPlugin javaPlugin;

    public CommandHandler(final JavaPlugin javaPlugin) {
        this.commands = new ArrayList<ICommand>();
        this.javaPlugin = javaPlugin;
        this.registerCommand(new MasssayCommand(), true);
        this.registerCommand(new SudoCommand(), true);
        this.registerCommand(new MoreCommand(), true);
        this.registerCommand(new DisguiseCommand(), true);
        this.registerCommand(new DisguiseListCommand(), true);
        this.registerCommand(new unDisguiseCommand(), true);
    }

    private void registerCommand(final ICommand cmd, final boolean single) {
        if (!this.commands.contains(cmd)) {
            this.commands.add(cmd);
            if (single && cmd.getClass().isAnnotationPresent(Command.class)) {
                final Command command = cmd.getClass().getAnnotation(Command.class);
                this.javaPlugin.getCommand(command.name()).setExecutor((CommandExecutor)this);
            }
        }
    }

    public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command cmd, final String s, final String[] args) {
        for (final ICommand pCmd : this.commands) {
            if (pCmd.getClass().isAnnotationPresent(Command.class)) {
                final Command command = pCmd.getClass().getAnnotation(Command.class);
                if (!command.name().equalsIgnoreCase(cmd.getName())) {
                    continue;
                }
                if (!sender.hasPermission(command.permission()) && !command.permission().equals("")) {
                    if (!command.noPerm().equals("")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', command.noPerm()));
                    }
                    else {
                        sender.sendMessage(ChatColor.RED + "You do not have permission to use that command.");
                    }
                    return true;
                }
                if (args.length < command.minArgs()) {
                    sender.sendMessage(ChatColor.RED + "Usage: " + command.usage());
                    return true;
                }
                if (command.playerOnly() && !(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.RED + "This is a player only command.");
                    return true;
                }
                pCmd.onCommand(new CmdArgs(sender, args));
                return true;
            }
        }
        return true;
    }

    public List<ICommand> getCommands() {
        return this.commands;
    }
}
