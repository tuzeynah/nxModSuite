package me.dexter.modsuite.command.handler;

import org.bukkit.command.*;
import lombok.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import java.beans.*;

public class CmdArgs
{
    @NonNull
    private CommandSender sender;
    @NonNull
    private String[] args;

    public String getJoinedArgs(final int start, final int end) {
        if (this.args.length < start) {
            return "";
        }
        String s = "";
        for (int i = start; i < ((end >= 0) ? end : this.args.length); ++i) {
            s = String.valueOf(s) + this.args[i] + " ";
        }
        if (s.length() > 2) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    public String[] getArgs() {
        return this.args;
    }

    private String[] filterFlags(final String[] a) {
        int x = 0;
        int removed = 0;
        final String[] newArgs = new String[a.length];
        for (int i = 0; i < a.length; ++i) {
            final String s = a[i];
            if (!s.startsWith("-")) {
                newArgs[x] = s;
                ++x;
            }
            else {
                ++removed;
            }
        }
        final String[] xArgs = new String[a.length - removed];
        for (int j = 0; j < newArgs.length; ++j) {
            xArgs[j] = newArgs[j];
        }
        return xArgs;
    }

    public boolean hasFlags() {
        for (int i = 0; i < this.args.length; ++i) {
            if (this.args[i].startsWith("-")) {
                return true;
            }
        }
        return false;
    }

    public int getFlagsCount() {
        int x = 0;
        for (int i = 0; i < this.args.length; ++i) {
            if (this.args[i].startsWith("-")) {
                ++x;
            }
        }
        return x;
    }

    public String[] getFlags() {
        final String[] flags = new String[this.getFlagsCount()];
        int x = 0;
        for (int i = 0; i < this.args.length; ++i) {
            if (this.args[i].startsWith("-")) {
                flags[x] = this.args[i];
                ++x;
            }
        }
        return flags;
    }

    public boolean hasFlag(final String flag) {
        final String[] flags = this.getFlags();
        for (int i = 0; i < flags.length; ++i) {
            final String s = flags[i];
            if (s.equalsIgnoreCase(flag) || s.contains(flag)) {
                return true;
            }
        }
        return false;
    }

    public Player getPlayer(final int arg) {
        return Bukkit.getPlayer(this.getArg(arg));
    }

    public String matchPlayer(final int arg) {
        String s = this.getArg(arg);
        final Player t = Bukkit.getPlayer(s);
        if (t != null) {
            s = t.getName();
        }
        return s;
    }

    public String matchPlayer(String s) {
        final Player t = Bukkit.getPlayer(s);
        if (t != null) {
            s = t.getName();
        }
        return s;
    }

    public String getJoinedArgs(final int start) {
        return this.getJoinedArgs(start, -1);
    }

    public String getArg(final int index) {
        return this.args[index];
    }

    public String[] trim(final int start) {
        final String[] newArgs = new String[this.args.length - start];
        int x = 0;
        for (int i = start; i < this.args.length; ++i) {
            newArgs[x] = this.args[i];
            ++x;
        }
        return newArgs;
    }

    public String[] trim(final int start, final int end) {
        final String[] newArgs = new String[this.args.length - start];
        int x = 0;
        for (int i = start; i < ((end >= 0) ? end : this.args.length); ++i) {
            newArgs[x] = this.args[i];
            ++x;
        }
        return newArgs;
    }

    @NonNull
    public CommandSender getSender() {
        return this.sender;
    }

    public void setArgs(@NonNull final String[] args) {
        if (args == null) {
            throw new NullPointerException("args");
        }
        this.args = args;
    }

    @ConstructorProperties({ "sender", "args" })
    public CmdArgs(@NonNull final CommandSender sender, @NonNull final String[] args) {
        if (sender == null) {
            throw new NullPointerException("sender");
        }
        if (args == null) {
            throw new NullPointerException("args");
        }
        this.sender = sender;
        this.args = args;
    }
}
