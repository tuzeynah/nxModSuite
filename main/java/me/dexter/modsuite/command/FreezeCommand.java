package me.dexter.modsuite.command;

import me.dexter.modsuite.command.handler.CmdArgs;
import me.dexter.modsuite.command.handler.Command;
import me.dexter.modsuite.command.handler.ICommand;
import me.dexter.modsuite.manager.FrozenManager;
import me.dexter.modsuite.nxModSuite;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


@Command(name = "freeze", usage = "/freeze <player>", minArgs = 1, playerOnly = true, permission = "modsuite.freeze")
public class FreezeCommand implements ICommand {
    @Override
    public void onCommand(final CmdArgs cmdArgs) {
        Player player = (Player) cmdArgs.getSender();

        Player target = cmdArgs.getPlayer(0);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "Player not found.");
            return;
        }
        if (FrozenManager.isFrozen(target)) {
            player.sendMessage(ChatColor.GREEN + "You have been unfrozen " + target.getName());
            nxModSuite.getInstance().getServer().broadcast(ChatColor.RED + target.getName() + ChatColor.YELLOW + " has been unfrozen by " + player.getName(), "core.staff");
            this.unfreezePlayer(target);
            return;
        }
        player.sendMessage(ChatColor.GREEN + "You have been frozen " + target.getName());
        nxModSuite.getInstance().getServer().broadcast(ChatColor.RED + target.getName() + ChatColor.GOLD + " has been frozen by " + player.getName(), "core.staff");
        this.freezePlayer(target);
        return;
    }

    private void freezePlayer(final Player player) {
        FrozenManager.freezeUUID(player);
        player.sendMessage(ChatColor.GREEN + "You have been frozen! Don't logout!");
        player.setWalkSpeed(0.0f);
        player.updateInventory();
       // player.openInventory(this.plugin.getManagerHandler().getInventoryManager().getFrozenInv());
    }

    private void unfreezePlayer(final Player player) {
        FrozenManager.unfreezeUUID(player);
        player.sendMessage(ChatColor.GREEN + "You have been unfrozen by staff.");
        player.closeInventory();
    }
}
