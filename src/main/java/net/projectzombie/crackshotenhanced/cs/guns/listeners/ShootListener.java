/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.cs.guns.listeners;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import com.shampaggon.crackshot.events.WeaponPreShootEvent;
import com.shampaggon.crackshot.events.WeaponPrepareShootEvent;
import com.shampaggon.crackshot.events.WeaponShootEvent;
import net.projectzombie.crackshotenhanced.cs.guns.physical.weps.CrackshotGunLore;
import net.projectzombie.crackshotenhanced.cs.guns.weps.Guns;
import net.projectzombie.crackshotenhanced.cs.guns.physical.weps.CrackshotGunItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Jesse Bannon (jmbannon@uw.edu)
 * 
 * Gun decay happens within the lore of the item of the weapon. There are two
 * lines that the listener reads from to determine gun durability and accuracy.
 * 
 * These lines appear to be invisible from the player's perspective, but
 * are actually converted to HEX and have the chat symbol placed in front
 * of them to act as colors with no text. These lines are encoded and decoded
 * through the HiddenStringUtils class.
 * 
 * Durability lore string:
 * PZ==durability==maxDurability==ConditionType==BuildType
 * 
 * Stat lore string:
 * PZ==gunType==CSBulletSpread==ScopeType==FireModeType
 * 
 */
public class ShootListener implements Listener
{   
    public ShootListener() { /* Do nothing */ }
    
    /**
     * Right before the weapon fires, method will check to see if custom 
     * durability has been initialized within the lore. Initializes it if it
     * has not, then decrements the durability by one.
     * 
     * @param event Event before the weapon fires. 
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void preDecayEvent(WeaponPreShootEvent event)
    {
        final Player shooter = event.getPlayer();
        
        final double bulletSpread = shoot(event.getBulletSpread(), shooter.getItemInHand());
        
        if (bulletSpread <= -2)
        {
            shooter.sendMessage("A parsing error has occured. Please consult an admin.");
            event.setCancelled(true);
        }
        else if (bulletSpread < 0)
        {
            shooter.sendMessage("Weapon's configuration is not encrypted. Please consult an admin.");
            event.setCancelled(true);
        }
        else if (bulletSpread == 0)
        {
            event.setSounds(null);
            event.setCancelled(true);
        }
        else
        {
            //Bukkit.broadcastMessage("" + bulletSpread);
            event.setBulletSpread(bulletSpread);
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void preDecayEvent(WeaponPrepareShootEvent event)
    {
        final Player shooter = event.getPlayer();
        final CrackshotGunItemStack gunItem = new CrackshotGunItemStack(shooter.getItemInHand());
        if (gunItem.isBroken())
        {
            shooter.sendMessage("Your " + event.getWeaponTitle() + " is broken.");
            event.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void test(WeaponShootEvent event)
    {
        final Entity proj = event.getProjectile();
        final String gunID = getGunID(event.getPlayer());
        
        if (gunID == null)
        {
            System.out.println("Gun id is null");
        }
        else
        {
            proj.setCustomName(gunID);
            proj.setCustomNameVisible(false);
            System.out.println("shoot vec " + proj.getVelocity().toString());
        }
    }
    
    /**
     * Testing
     * @param event 
     */
    @EventHandler(priority = EventPriority.LOWEST)
    private void onHitEvent(WeaponDamageEntityEvent event)
    {
        
        final String gunID = event.getDamager().getCustomName();
        final Guns.CrackshotGun gun = Guns.get(gunID);
        
        if (gun != null)
        {
            System.out.println("Setting damage: " + gun.getDamageOnEntityHit(event.isHeadshot()));
            event.setDamage(gun.getDamageOnEntityHit(event.isHeadshot()));
        }
        else
        {
            System.out.println("Can not find gun with ID: " + gunID);
        }
    }
    
    private static String getGunID(final Player player)
    {
        return new CrackshotGunLore(player.getItemInHand().getItemMeta().getLore()).getGunIDStr();
    }
    
    public static Guns.CrackshotGun getGun(final Player player)
    {
        return Guns.get(getGunID(player));
    }
    
    /**
     * 
     * @param eventBulletSpread
     * @param item
     * @return  leq 0 - An error occurred in the parsing.
     *           eq 0 - The weapon is broken
     *          geq 0 - The bullet spread to set for the event
     */
    public static double shoot(final double eventBulletSpread,
                               final ItemStack item)
    {
        final int ERROR = -2;
        final CrackshotGunItemStack csItem = new CrackshotGunItemStack(item);
        final ItemMeta gunMeta = item.getItemMeta();
        
        if (!item.hasItemMeta() || !item.getItemMeta().hasLore())
            return ERROR;

        if (csItem.isPreShot())
        {
            System.out.println("IS PRE SHOT");
            csItem.toPostShotLore();
            csItem.decrementDurability();
            gunMeta.setLore(csItem.getLore());
        }
        else if (csItem.isPostShot())
        {
            System.out.println("IS POST SHOT");
            csItem.decrementDurability();
            gunMeta.setLore(csItem.getLore());
        }
        else {
            System.out.println("IS ERROR");
            return ERROR;
        }

        item.setItemMeta(gunMeta);
        return csItem.getEventBulletSpread(eventBulletSpread);
    }
    
}
