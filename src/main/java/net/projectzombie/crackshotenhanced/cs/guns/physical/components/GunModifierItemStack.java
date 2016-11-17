/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.physical.components;

import net.projectzombie.crackshotenhanced.cs.guns.components.GunModifier;
import net.projectzombie.crackshotenhanced.cs.guns.crafting.GunModifierType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author jb
 */
public class GunModifierItemStack extends GunModifierLore
{

    public GunModifierItemStack(final ItemStack item)
    {
        super(hasLore(item) ? item.getItemMeta().getLore() : null);
    }
    
    public GunModifierItemStack(final GunModifierType type,
                                final int id)
    {
        super(type, id);
    }
    
    public ItemStack toItem()
    {
        if (!this.isValid())
            return null;
        
        final GunModifier mod = super.getGunModifier();
        final GunModifierType type = super.getGunModifierType();
        final Material material = type.getMaterial();
        final int itemStackAmount = 1;
        final boolean noItalics = false;
        
        if (material != null)
        {
            final ItemStack item = new ItemStack(material, itemStackAmount, type.getMaterialDataShort());

            ItemMeta meta = item.getItemMeta();
            meta.setLore(super.generateLore());
            meta.setDisplayName(mod.getDisplayName(noItalics));
            item.setItemMeta(meta);

            return item;
        }
        return null;
    }
    
    static private boolean hasLore(final ItemStack item)
    {
        return item.hasItemMeta()
            && item.getItemMeta().hasLore();
    }

    
    
}
