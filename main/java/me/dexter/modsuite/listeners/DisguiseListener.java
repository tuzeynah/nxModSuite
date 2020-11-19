package me.dexter.modsuite.listeners;

import com.verispvp.core.api.CoreAPI;
import com.verispvp.core.api.minecrafter.Minecrafter;
import com.verispvp.core.api.rank.Rank;
import me.dexter.modsuite.api.SkinAPI;
import me.dexter.modsuite.command.DisguiseCommand;
import me.dexter.modsuite.manager.DisguiseManager;
import me.dexter.modsuite.nxModSuite;
import me.dexter.modsuite.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent ;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
import xyz.haoshoku.nick.NickPlugin;
import xyz.haoshoku.nick.api.NickAPI;

public class DisguiseListener implements Listener {

    public void disgusiechoose(String realname, String fakename, Inventory lol, int slot) {
        final ItemStack PlayerItem = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        final SkullMeta PlayerItemHeadMeta = (SkullMeta)PlayerItem.getItemMeta();
        PlayerItemHeadMeta.setOwner(realname);
        PlayerItemHeadMeta.setDisplayName("§9" + fakename);
        PlayerItem.setItemMeta((ItemMeta)PlayerItemHeadMeta);

        lol.setItem(slot, PlayerItem);
    }

    public void disgusieinv(final Player player) {
        Inventory disgusie = Bukkit.createInventory(null, 36, "Pick a skin");
        final ItemStack glass = new ItemBuilder(Material.STAINED_GLASS_PANE, (short)15).setDisplayName(" ").build();
        final ItemStack placa = new ItemBuilder(Material.SIGN).setDisplayName("§6§lNX §7§m- §fDisguise").setLore("§7still in beta.").build();

        disgusiechoose("xJaron", "Zelda", disgusie, 0);
        disgusiechoose("TheSuperman", "Superman", disgusie, 1);
        disgusiechoose("horlando", "horlando", disgusie, 2);
        disgusiechoose("02z", DisguiseManager.fake.get(player), disgusie, 5);
        disgusiechoose("yanizenastar", "Yato", disgusie, 6);
        disgusiechoose("pre", "Old Marcel", disgusie, 7);
        disgusiechoose("Alhu_Akbar", "Alahu akbar", disgusie, 8);
        disgusiechoose("Herobrine", "Herobrine", disgusie, 9);
        disgusiechoose("Amsi2008", "EGirl", disgusie, 10);
        disgusiechoose("BCZ", "Bcz", disgusie, 11);
        disgusiechoose("Zephhyre", "Zephhyre", disgusie, 12);
        disgusiechoose("Verzide", "Verzide", disgusie, 13);
        disgusiechoose("ImHacking", "ImHacking", disgusie, 14);
        disgusiechoose("FPS", "Fps", disgusie, 15);
        disgusiechoose("abecker3", "Batman", disgusie, 16);
        disgusiechoose("96baconbits", "Zelda2", disgusie, 17);
        disgusiechoose("5emaj", "Ash", disgusie, 18);
        disgusiechoose("ADZ25", "Bear", disgusie, 19);
        disgusiechoose("iProCombo", "iProCombo", disgusie, 20);
        disgusiechoose("Stimpay", "Stimpay", disgusie, 21);
        disgusiechoose("StimpyPvP", "StimpyPvP", disgusie, 22);
        disgusiechoose("Beach", "Beach", disgusie, 23);
        disgusiechoose("Sasuke", "Sasuke", disgusie, 24);
        disgusiechoose("Naruto", "Naruto", disgusie, 25);
        disgusiechoose("Minato", "Minato", disgusie, 26);
        disgusiechoose("Nagato", "Nagato", disgusie, 27);
        disgusiechoose("Madara", "Madara", disgusie, 28);
        disgusiechoose("Day", "Day", disgusie, 29);
        disgusiechoose("Jewdah", "Jewdah", disgusie, 30);
        disgusiechoose("Idiol", "Idiol", disgusie, 31);

        disgusie.setItem(4, glass);
        disgusie.setItem(3, placa);
        player.openInventory(disgusie);
    }

    public static void wait(final int ms) {
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    @EventHandler
    public void onJoinWhileDisguise(PlayerJoinEvent event) {
        Player player = (Player)event.getPlayer();
        if (DisguiseManager.fake.containsKey(player))
        {
            DisguiseManager.fakes.remove(DisguiseManager.fake.get(player));
            DisguiseManager.fake.remove(player);
            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "rank set " + (String)DisguiseManager.rankName.get(player) + " " + (String)DisguiseManager.realName.get(player));
            DisguiseManager.realName.remove(player);
        }
    }

    public void DisgusiePlayer(Player player, String skin) {
        NickAPI api = NickPlugin.getPlugin().getAPI();
        api.setSkin(player, skin);
        api.refreshPlayer(player);

        SkinAPI.changeNick(player, (String)DisguiseManager.fake.get(player));
        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "rank set Member "  + (String)DisguiseManager.realName.get(player));

        player.closeInventory();
        player.sendMessage("§aSuccess! You now look like " + (String)DisguiseManager.fake.get(player) + "! (In the skin of §e" + skin + "§a)");
        player.sendMessage("§aIf your name appears white on the tab don't worry it appears just for you.");
    }

    @EventHandler
    public void onInventory(final InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            if (event.getInventory().getTitle().equals("Pick a skin")) {
                final Player player = (Player)event.getWhoClicked();
                event.setCancelled(true);
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Zelda")) {
                    DisgusiePlayer(player, "xJaron");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Superman")) {
                    DisgusiePlayer(player, "TheSuperman");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9horlando")) {
                    DisgusiePlayer(player, "horlando");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9" + DisguiseManager.fake.get(player))) {
                    DisgusiePlayer(player, DisguiseManager.fake.get(player));
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Yato")) {
                    DisgusiePlayer(player, "yanizenastar");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Old Marcel")) {
                    DisgusiePlayer(player, "pre");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Alahu akbar")) {
                    DisgusiePlayer(player, "Alhu_Akbar");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Herobrine")) {
                    DisgusiePlayer(player, "Herobrine");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9EGirl")) {
                    DisgusiePlayer(player, "Amsi2008");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Bcz")) {
                    DisgusiePlayer(player, "BCZ");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Zephhyre")) {
                    DisgusiePlayer(player, "Zephhyre");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Verzide")) {
                    DisgusiePlayer(player, "Verzide");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9ImHacking")) {
                    DisgusiePlayer(player, "ImHacking");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Fps")) {
                    DisgusiePlayer(player, "FPS");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Batman")) {
                    DisgusiePlayer(player, "Batman");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Ash")) {
                    DisgusiePlayer(player, "5emaj");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Zelda2")) {
                    DisgusiePlayer(player, "96baconbits");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Bear")) {
                    DisgusiePlayer(player, "ADZ25");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9iProCombo")) {
                    DisgusiePlayer(player, "iProCombo");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Stimpay")) {
                    DisgusiePlayer(player, "Stimpay");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9StimpyPvP")) {
                    DisgusiePlayer(player, "StimpyPvP");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Beach")) {
                    DisgusiePlayer(player, "Beach");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Sasuke")) {
                    DisgusiePlayer(player, "Sasuke");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Naruto")) {
                    DisgusiePlayer(player, "Naruto");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Minato")) {
                    DisgusiePlayer(player, "Minato");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Nagato")) {
                    DisgusiePlayer(player, "Nagato");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Madara")) {
                    DisgusiePlayer(player, "Madara");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Day")) {
                    DisgusiePlayer(player, "Day");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Jewdah")) {
                    DisgusiePlayer(player, "Jewdah");
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§9Idiol")) {
                    DisgusiePlayer(player, "Idiol");
                }
            }


            if (event.getInventory().getTitle().equals("Pick a rank")) {
                final Player player = (Player)event.getWhoClicked();
                if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§aDefault Rank")) {
                    player.closeInventory();
                    this.disgusieinv(player);
                }
            }
        }
    }
}
