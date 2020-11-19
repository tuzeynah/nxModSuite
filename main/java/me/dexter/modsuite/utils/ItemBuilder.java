package me.dexter.modsuite.utils;

import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.*;
import java.util.*;
import org.bukkit.enchantments.*;

public class ItemBuilder
{
    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(final Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder(final Material material, final short subID) {
        this.itemStack = new ItemStack(material, 1, subID);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder setDisplayName(final String name) {
        this.itemMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setLore(final String... lore) {
        this.itemMeta.setLore((List)Arrays.asList(lore));
        return this;
    }

    public ItemBuilder addEnchantment(final Enchantment enchantment, final int level) {
        this.itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder setAmount(final int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setUnbreakable(final Boolean unbreakable) {
        this.itemMeta.spigot().setUnbreakable((boolean)unbreakable);
        return this;
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }
}
