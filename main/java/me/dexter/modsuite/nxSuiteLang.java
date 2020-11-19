package me.dexter.modsuite;

import com.verispvp.core.api.CoreAPI;
import com.verispvp.core.api.minecrafter.Minecrafter;
import org.bukkit.entity.Player;

public class nxSuiteLang {

    private  Player player;

    public nxSuiteLang(Player player) {
        this.player = player;
    }

    public String getColoredName() {
        Minecrafter playerprofile = CoreAPI.INSTANCE.getMinecrafter(this.player);
        return playerprofile.getRank().getColor() + player.getName();
    }

    public String getRankName()
    {
        Minecrafter playerprofile = CoreAPI.INSTANCE.getMinecrafter(this.player);
        return playerprofile.getRank().getName();
    }

    public String getRankPrefix()
    {
        Minecrafter playerprofile = CoreAPI.INSTANCE.getMinecrafter(this.player);
        return playerprofile.getRank().getPrefix();
    }

}
