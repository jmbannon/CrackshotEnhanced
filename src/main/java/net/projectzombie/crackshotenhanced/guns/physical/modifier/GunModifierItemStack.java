/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.physical.modifier;

import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import net.projectzombie.crackshotenhanced.guns.components.skeleton.GunSkeletons;
import net.projectzombie.crackshotenhanced.guns.crafting.CraftableType;
import net.projectzombie.crackshotenhanced.guns.physical.PhysicalItemStack;
import net.projectzombie.crackshotenhanced.guns.utilities.ItemStackUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GunModifierItemStack extends GunModifierLore implements PhysicalItemStack
{

    public GunModifierItemStack(final ItemStack item)
    {
        super(ItemStackUtils.hasLore(item) ? item.getItemMeta().getLore() : null);
    }
    
    public GunModifierItemStack(final CraftableType type,
                                final int id)
    {
        super(type, id);
    }

    @Override
    public ItemStack toItemStack()
    {
        if (this.getGunModifierType().equals(CraftableType.SKELETON)) {
            return GunSkeletons.getInstance().get(this.getID()).toItemStack();
        }

        if (!this.isValid())
            return null;
        
        final GunModifier mod = super.getGunModifier();
        final CraftableType type = super.getGunModifierType();
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


}
