package me.dexter.modsuite.command;


import me.dexter.modsuite.command.handler.CmdArgs;
import me.dexter.modsuite.command.handler.Command;
import me.dexter.modsuite.command.handler.ICommand;
import me.dexter.modsuite.manager.DisguiseManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Command(name = "disguiselist", usage = "/disguiselist", playerOnly = true)
public class DisguiseListCommand implements ICommand {

    public void getPlayersDisguised(Player player) {
        for (Player onlines : Bukkit.getOnlinePlayers()) {
            if (DisguiseManager.fake.containsKey(onlines)){
                player.sendMessage("   §6" + DisguiseManager.realName.get(onlines) + " -> §f" + DisguiseManager.fake.get(onlines));
            }
        }
    }

    public void execute(Player player) {

        player.sendMessage("§7§m-------------------------------------------------");
        player.sendMessage("  §6§lNX §7- §fDisguised Players: ");
        getPlayersDisguised(player);
        player.sendMessage("§7§m-------------------------------------------------");
    }

    @Override
    public void onCommand(final CmdArgs cmdArgs) {

        Player player = (Player) cmdArgs.getSender();

        if (DisguiseManager.fake.containsKey(player))
        {
            execute(player);
        }
        else {
            if (player.hasPermission("core.command.disguiselist")) {
                execute(player);
            }
            else {
                player.sendMessage("§cYou don't have permission to use that command.");
                return;
            }
        }
    }
}
