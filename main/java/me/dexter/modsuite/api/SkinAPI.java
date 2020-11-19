package me.dexter.modsuite.api;

import me.dexter.modsuite.nxModSuite;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import com.mojang.authlib.*;
import com.mojang.authlib.properties.*;
import java.lang.reflect.*;
import org.bukkit.craftbukkit.v1_8_R3.inventory.*;
import org.bukkit.craftbukkit.v1_8_R3.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import java.util.*;
import net.minecraft.server.v1_8_R3.*;
import me.dexter.modsuite.api.GameProfilesFetcher;
import xyz.haoshoku.nick.NickPlugin;
import xyz.haoshoku.nick.api.NickAPI;

public class SkinAPI
{
    public static HashMap<String, String> nicks;

    static {
        SkinAPI.nicks = new HashMap<String, String>();
    }

    public static void changeSkin(final Player p, final String skin) {
        final CraftPlayer cp = (CraftPlayer)p;
        GameProfile skingp = cp.getProfile();
        try {
            if (me.dexter.modsuite.api.UUIDFetcher.getUUID(skin) == null) {
                skingp = GameProfilesFetcher.fetch(me.dexter.modsuite.api.UUIDFetcher.getUUID("Steve"));
            }
            else {
                skingp = GameProfilesFetcher.fetch(me.dexter.modsuite.api.UUIDFetcher.getUUID(skin));
            }
        }
        catch (Exception ex) {}
        final Collection<Property> props = (Collection<Property>)skingp.getProperties().get("textures");
        cp.getProfile().getProperties().removeAll((Object)"textures");
        cp.getProfile().getProperties().putAll("textures", (Iterable)props);
    }

    public static String getRealName(final Player p) {
        return SkinAPI.nicks.get(p.getUniqueId().toString());
    }

    public static void RemoveDisgusie(Player p, String oldname, String oldrank) {
        NickAPI api = NickPlugin.getPlugin().getAPI();

        api.setSkin(p, getRealName(p));
        changeNick(p, getRealName(p));
        changeNick(p, oldname);

        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "setrank " + getRealName(p) + " " + oldrank);

        SkinAPI.nicks.remove(p.getUniqueId().toString());

        api.refreshPlayer(p);

        p.kickPlayer("§aSuccessfully undisguised! retry and be fun!");
    }

    public static void changeNick(final Player p, final String nick) {
        SkinAPI.nicks.put(p.getUniqueId().toString(), p.getName());
        final CraftPlayer cp = (CraftPlayer)p;
        try {
            final Field pF = cp.getProfile().getClass().getDeclaredField("name");
            pF.setAccessible(true);
            pF.set(cp.getProfile(), nick);
        }
        catch (Exception ex) {}
    }

    public static void updateSkin(final Player p) {
        try {
            if (!p.isOnline()) {
                return;
            }
            final CraftPlayer cp = (CraftPlayer)p;
            final EntityPlayer ep = cp.getHandle();
            final int entId = ep.getId();
            final PacketPlayOutPlayerInfo removeInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, new EntityPlayer[] { ep });
            final PacketPlayOutEntityDestroy removeEntity = new PacketPlayOutEntityDestroy(new int[] { entId });
            final PacketPlayOutNamedEntitySpawn addNamed = new PacketPlayOutNamedEntitySpawn((EntityHuman)ep);
            final PacketPlayOutPlayerInfo addInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[] { ep });
            final PacketPlayOutRespawn respawn = new PacketPlayOutRespawn(ep.getWorld().worldProvider.getDimension(), ep.getWorld().getDifficulty(), ep.getWorld().worldData.getType(), WorldSettings.EnumGamemode.getById(p.getGameMode().getValue()));
            final PacketPlayOutEntityEquipment itemhand = new PacketPlayOutEntityEquipment(entId, 0, CraftItemStack.asNMSCopy(p.getItemInHand()));
            final PacketPlayOutEntityEquipment helmet = new PacketPlayOutEntityEquipment(entId, 4, CraftItemStack.asNMSCopy(p.getInventory().getHelmet()));
            final PacketPlayOutEntityEquipment chestplate = new PacketPlayOutEntityEquipment(entId, 3, CraftItemStack.asNMSCopy(p.getInventory().getChestplate()));
            final PacketPlayOutEntityEquipment leggings = new PacketPlayOutEntityEquipment(entId, 2, CraftItemStack.asNMSCopy(p.getInventory().getLeggings()));
            final PacketPlayOutEntityEquipment boots = new PacketPlayOutEntityEquipment(entId, 1, CraftItemStack.asNMSCopy(p.getInventory().getBoots()));
            final PacketPlayOutHeldItemSlot slot = new PacketPlayOutHeldItemSlot(p.getInventory().getHeldItemSlot());
            for (final Player pOnline : ((CraftServer)Bukkit.getServer()).getOnlinePlayers()) {
                final CraftPlayer craftOnline = (CraftPlayer)pOnline;
                final PlayerConnection con = craftOnline.getHandle().playerConnection;
                if (pOnline.getName().equals(p.getName())) {
                    con.sendPacket((Packet)removeInfo);
                    con.sendPacket((Packet)addInfo);
                    con.sendPacket((Packet)respawn);
                    con.sendPacket((Packet)slot);
                    craftOnline.updateScaledHealth();
                    craftOnline.getHandle().triggerHealthUpdate();
                    craftOnline.updateInventory();
                    Bukkit.getScheduler().runTask((Plugin) nxModSuite.getInstance(), (Runnable)new Runnable() {
                        @Override
                        public void run() {
                            craftOnline.getHandle().updateAbilities();
                        }
                    });
                    final PacketPlayOutPosition position = new PacketPlayOutPosition(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch(), (Set)new HashSet());
                    con.sendPacket((Packet)position);
                }
                else {
                    if (!pOnline.canSee(p)) {
                        continue;
                    }
                    con.sendPacket((Packet)removeEntity);
                    con.sendPacket((Packet)removeInfo);
                    con.sendPacket((Packet)addInfo);
                    con.sendPacket((Packet)addNamed);
                    con.sendPacket((Packet)itemhand);
                    con.sendPacket((Packet)helmet);
                    con.sendPacket((Packet)chestplate);
                    con.sendPacket((Packet)leggings);
                    con.sendPacket((Packet)boots);
                }
            }
        }
        catch (Exception ex) {}
    }

    public static void updateForOther(Player p) {

        CraftPlayer cp = (CraftPlayer)p;
        EntityPlayer ep = cp.getHandle();
        int entId = ep.getId();

        PacketPlayOutPlayerInfo removeInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, new EntityPlayer[] { ep });

        PacketPlayOutEntityDestroy removeEntity = new PacketPlayOutEntityDestroy(new int[] { entId });

        PacketPlayOutNamedEntitySpawn addNamed = new PacketPlayOutNamedEntitySpawn(ep);

        PacketPlayOutPlayerInfo addInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[] { ep });

        Bukkit.getOnlinePlayers().forEach(all -> {
            if(all != p) {
                CraftPlayer cpAll = (CraftPlayer)all;

                PlayerConnection con = cpAll.getHandle().playerConnection;
                con.sendPacket(removeEntity);
                con.sendPacket(removeInfo);
                con.sendPacket(addInfo);
                con.sendPacket(addNamed);
            }
        });
    }
}
