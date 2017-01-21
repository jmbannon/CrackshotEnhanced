/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.physical.gun;

import net.projectzombie.crackshotenhanced.guns.physical.PhysicalItemStack;
import net.projectzombie.crackshotenhanced.guns.utilities.Constants;
import net.projectzombie.crackshotenhanced.guns.gun.CrackshotGun;
import net.projectzombie.crackshotenhanced.guns.utilities.ItemStackUtils;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Jesse Bannon
 * 
 * Represents a CrackshotGun in the form of an ItemStack. To be used
 * in an event when a player is holding an ItemStack.
 */
public class CrackshotGunItemStack extends CrackshotGunLore implements PhysicalItemStack
{
    private final ItemStack gunItem;
    
    /**
     * Initializes the CrackshotGun ItemStack. If the ItemStack does not
     * contain lore the item is set to null. TODO: Throw exception.
     * 
     * @param item ItemStack to size as CrackshotGun.
     */
    public CrackshotGunItemStack(final ItemStack item)
    {
        super(ItemStackUtils.hasLore(item) ? item.getItemMeta().getLore() : null);
        this.gunItem = item;
    }

    public CrackshotGunItemStack(final CrackshotGun gun) {
        super(gun.getGunID());
        this.gunItem = gun.getBareItemStack();

        final ItemMeta meta = gunItem.getItemMeta();

        // Gets the ammo name from generating the Crackshot weapon
        final String metaName = Constants.CRACKSHOT.generateWeapon(gun.getCSWeaponName()).getItemMeta().getDisplayName();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(this.generateLore());
        meta.setDisplayName(metaName);
        this.gunItem.setItemMeta(meta);
    }

    /**
     * Returns a string that contains the modified name with respect to the 
     * new GunMeta. Also sets the ammo amount to be the same.
     * 
     * @param newGunMeta ItemMeta of the newly modified version.
     * @return Modified name and ammo amount.
     */
    private String getModifiedName(final ItemMeta newGunMeta)
    {
        final String origName = gunItem.getItemMeta().getDisplayName();
        final String newName = newGunMeta.getDisplayName();
        
        String ammoSubString = origName.substring(origName.indexOf("«"), origName.indexOf("»"));
        String adjustedNewName = newName.substring(0, newName.indexOf("«")) + ammoSubString + newName.substring(newName.indexOf("»"), newName.length());
        
        return adjustedNewName;
    }
    
    /**
     * Returns the ItemStack of a modified gun.
     * 
     * @param newGun Modified version of current gun.
     * @return ItemStack of the new weapon.
     */
    public ItemStack getModifiedGunItem(final CrackshotGun newGun)
    {
        final ItemStack newGunItem = Constants.CRACKSHOT.generateWeapon(newGun.getCSWeaponName());
        if (newGunItem == null || !this.isValid())
            return null;

        final ItemMeta newMeta = newGunItem.getItemMeta();
        final CrackshotGunLore newLore = new CrackshotGunLore(newMeta.getLore());

        newLore.setGunID(newGun.getUniqueID());;
        newMeta.setLore(newLore.generateLore());
        newMeta.setDisplayName(getModifiedName(newMeta));
        newGunItem.setItemMeta(newMeta);
        return newGunItem;
    }


    @Override
    public boolean shoot() {
        final ItemMeta gunMeta = gunItem.getItemMeta();
        if (this.isPreShot()) {
            gunMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }
        final boolean validShoot = super.shoot();
        if (validShoot) {
            gunMeta.setLore(this.getLore());
            gunItem.setItemMeta(gunMeta);
        }
        return validShoot;

    }

    @Override public boolean isValid() {
        return ItemStackUtils.hasLore(gunItem) && super.isValid();
    }

    @Override public ItemStack toItemStack() {
        return gunItem;
    }
    
}
