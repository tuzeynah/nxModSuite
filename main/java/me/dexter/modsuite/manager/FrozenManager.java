package me.dexter.modsuite.manager;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class FrozenManager
{
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static ArrayList<Player> frozenUUIDs = new ArrayList();

    public static void freezeUUID(Player player) {
        frozenUUIDs.add(player);
    }

    public static void unfreezeUUID(Player player) {
        frozenUUIDs.remove(player);
    }

    public static boolean isFrozen(Player player) {
        return frozenUUIDs.contains(player);
    }
}