package me.dexter.modsuite.utils;

import me.dexter.modsuite.nxModSuite;

import java.util.List;

public class Message {

    public static String getString(String path) {
        return nxModSuite.getInstance().getConfig().getString(path).replace("&", "§");
    }

    public static List<String> getStringList(String path) {
        return nxModSuite.getInstance().getConfig().getStringList(path);
    }

    public static boolean getBoolean(String path) {
        return nxModSuite.getInstance().getConfig().getBoolean(path);
    }
}
