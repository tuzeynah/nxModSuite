package me.dexter.modsuite.command;

import com.verispvp.core.api.CoreAPI;
import com.verispvp.core.api.minecrafter.Minecrafter;
import me.dexter.modsuite.command.handler.CmdArgs;
import me.dexter.modsuite.command.handler.Command;
import me.dexter.modsuite.command.handler.ICommand;
import me.dexter.modsuite.manager.DisguiseManager;
import me.dexter.modsuite.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

@Command(name = "disguise", usage = "/disguise <name>", minArgs = 0, playerOnly = true, permission = "core.command.disguise")
public class DisguiseCommand implements ICommand {

    @Override
    public void onCommand(final CmdArgs cmdArgs) {
        Player player = (Player) cmdArgs.getSender();

        Minecrafter playerprofile = CoreAPI.INSTANCE.getMinecrafter(player);

        if (cmdArgs.getArgs().length == 0) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: /disguise <name>"));
            return;
        }

        if (playerprofile.isAdminChat()) {
            player.sendMessage("§aExit the admin chat to disguise");
            return;
        }

        if (playerprofile.isStaffChat()) {
            player.sendMessage("§aExit the staff chat to disguise");
            return;
        }

        if (DisguiseManager.fake.containsKey(player))
        {
            player.sendMessage("§cYou are still disguised! undisguised first!");
            return;
        }

        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.getName().contains(cmdArgs.getArg(0))) {
                player.sendMessage("§cYou cannot use a nickname of an online player as a fake nickname.");
                return;
            }
        }

        DisguiseManager.fakes.add(cmdArgs.getArg(0));
        DisguiseManager.fake.put(player, cmdArgs.getArg(0));
        DisguiseManager.realName.put(player, player.getName());
        DisguiseManager.rankName.put(player,playerprofile.getRank().getName());

        this.Inventory(player);
    }

    public void Inventory(final Player player) {
        final Inventory inv = Bukkit.createInventory((InventoryHolder)null, 27, "Pick a rank");
        final ItemStack amarelo = new ItemBuilder(Material.STAINED_GLASS_PANE, (short)15).setDisplayName(" ").build();

        final ItemStack Default = new ItemStack(Material.LEATHER_CHESTPLATE);
        final LeatherArmorMeta meta2 = (LeatherArmorMeta)Default.getItemMeta();
        meta2.setColor(Color.WHITE);
        meta2.setDisplayName("§aDefault Rank");
        Default.setItemMeta((ItemMeta)meta2);
        inv.setItem(0, amarelo);
        inv.setItem(1, amarelo);
        inv.setItem(2, amarelo);
        inv.setItem(3, amarelo);
        inv.setItem(4, amarelo);
        inv.setItem(5, amarelo);
        inv.setItem(6, amarelo);
        inv.setItem(7, amarelo);
        inv.setItem(8, amarelo);
        inv.setItem(9, amarelo);
        inv.setItem(17, amarelo);
        inv.setItem(18, amarelo);
        inv.setItem(19, amarelo);
        inv.setItem(20, amarelo);
        inv.setItem(21, amarelo);
        inv.setItem(22, amarelo);
        inv.setItem(23, amarelo);
        inv.setItem(24, amarelo);
        inv.setItem(25, amarelo);
        inv.setItem(26, amarelo);
        inv.setItem(10, Default);
        player.openInventory(inv);
    }

}
