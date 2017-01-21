package net.projectzombie.crackshotenhanced.guns.physical;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public interface PhysicalItemStack {
    ItemStack toItemStack();

    /**
     * Writes the ItemStack info to a yaml allowing users to use CSE ItemStacks in other plugin configurations.
     * @param yaml Yaml to write to.
     * @param item ItemStack to write info.
     * @return ConfigurationSection of the ItemStack.
     */
    static ConfigurationSection writeItemStackConfigurationSection(final YamlConfiguration yaml,
                                                                   final String rootName,
                                                                   final ItemStack item) {
        final ItemMeta meta = item.getItemMeta();
        final String displayPath = rootName + ".display";
        final String materialPath = rootName + ".material";
        final String idPath = rootName + ".id";
        final String dataPath = rootName + ".data";
        final String lorePath = rootName + ".lore";

        yaml.set(displayPath, meta.getDisplayName());
        yaml.set(materialPath, item.getType().toString());
        yaml.set(idPath, item.getTypeId());
        yaml.set(dataPath, (int)item.getData().getData());
        yaml.set(lorePath, meta.getLore());

        return yaml;
    }
}
