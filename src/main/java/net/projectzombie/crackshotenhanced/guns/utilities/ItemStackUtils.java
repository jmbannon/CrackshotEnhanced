package net.projectzombie.crackshotenhanced.guns.utilities;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ItemStackUtils {

    /**
     * @param item ItemStack to evaluate whether it has lore or not.
     * @return True of the ItemStack has lore. False otherwise.
     */
    static public boolean hasLore(final ItemStack item)
    {
        return item != null && item.hasItemMeta() && item.getItemMeta().hasLore();
    }

    /**
     * @param lore Lore to evaluate whether it contains anything in its lore.
     * @return True of the lore is not empty. False otherwise.
     */
    static public boolean hasLoreContents(final List<String> lore) {
        return lore != null && lore.size() > 0;
    }

    /**
     * @param item ItemStack to evaluate whether it contains anything in its lore.
     * @return True of the lore is not empty. False otherwise.
     */
    static public boolean hasLoreContents(final ItemStack item) {
        return hasLore(item) && hasLoreContents(item.getItemMeta().getLore());
    }

}
