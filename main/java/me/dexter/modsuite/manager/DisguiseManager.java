package me.dexter.modsuite.manager;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class DisguiseManager {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static ArrayList<String> fakes = new ArrayList();
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static HashMap<Player, String> fake = new HashMap();
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static HashMap<Player, String> realName = new HashMap();
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static HashMap<Player, String> rankName = new HashMap();
}