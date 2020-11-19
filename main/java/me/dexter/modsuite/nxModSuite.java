package me.dexter.modsuite;

import me.dexter.modsuite.command.handler.CommandHandler;
import me.dexter.modsuite.listeners.DisguiseListener;
import me.dexter.modsuite.listeners.PlayerListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class nxModSuite extends JavaPlugin {

    private static nxModSuite instance;
    private static CommandHandler commandHandler;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        nxModSuite.commandHandler = new CommandHandler(this);
        listeners();
    }

    public void listeners() {
        this.getServer().getPluginManager().registerEvents((Listener)new PlayerListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new DisguiseListener(), (Plugin)this);
    }

    @Override
    public void onDisable() {
        nxModSuite.commandHandler.getCommands().clear();
        nxModSuite.commandHandler = null;
    }


    public static CommandHandler getCommandHandler() { return nxModSuite.commandHandler; }
    public static nxModSuite getInstance() {
        return nxModSuite.instance;
    }
}
