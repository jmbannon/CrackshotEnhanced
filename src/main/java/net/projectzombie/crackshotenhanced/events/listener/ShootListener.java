/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.events.listener;

import com.shampaggon.crackshot.events.WeaponPreShootEvent;
import com.shampaggon.crackshot.events.WeaponPrepareShootEvent;
import com.shampaggon.crackshot.events.WeaponShootEvent;
import net.projectzombie.crackshotenhanced.entities.CSEPlayer;
import net.projectzombie.crackshotenhanced.guns.physical.gun.CrackshotGunLore;
import net.projectzombie.crackshotenhanced.guns.gun.CrackshotGun;
import net.projectzombie.crackshotenhanced.main.Main;
import net.projectzombie.crackshotenhanced.static_maps.ConnectedPlayers;
import net.projectzombie.crackshotenhanced.static_maps.Guns;
import net.projectzombie.crackshotenhanced.guns.physical.gun.CrackshotGunItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

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
        final Player player = event.getPlayer();
        final CSEPlayer shooter = ConnectedPlayers.get(player);
        final CrackshotGunItemStack physicalGun = shootCrackshotGunItemStack(
                shooter.toBukkit().getInventory().getItemInMainHand());
        
        if (physicalGun == null) {
            player.sendMessage("Invalid gun. Please consult an admin.");
            event.setCancelled(true);
        } else if (physicalGun.isBroken()) {
            event.setSounds(null);
            event.setCancelled(true);
            player.sendMessage("Your " + physicalGun.getGun().getName() + " is broken.");
        } else {
            final CrackshotGun gun = physicalGun.getGun();
            if (shooter.isMoving()) {
                Main.info("IS MOVING");
                event.setBulletSpread(gun.getRunningBulletSpread());
            } else if (shooter.isZoomed()) {
                Main.info("IS ZOOMED");
                event.setBulletSpread(gun.getZoomedBulletSpread());
            } else if (shooter.isCrouching()) {
                Main.info("IS CROUCH");
                event.setBulletSpread(gun.getCrouchingBulletSpread());
            } else /* shooter.isStanding() */ {
                Main.info("IS STANDING");
                event.setBulletSpread(gun.getStandingBulletSpread());
            }
        }

        player.updateInventory();
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void preDecayEvent(WeaponPrepareShootEvent event)
    {
        final Player shooter = event.getPlayer();
        final CrackshotGunItemStack gunItem = new CrackshotGunItemStack(shooter.getInventory().getItemInMainHand());
        if (gunItem.isBroken()) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void test(WeaponShootEvent event)
    {
        final Entity projectile = event.getProjectile();
        final String gunID = getGunID(event.getPlayer());
        if (gunID != null) {
            projectile.setCustomName(gunID);
            projectile.setCustomNameVisible(false);
        }
    }

    private static String getGunID(final Player player)
    {
        return new CrackshotGunLore(player.getInventory().getItemInMainHand().getItemMeta().getLore()).getGunIDStr();
    }
    
    public static CrackshotGun getGun(final Player player)
    {
        return Guns.get(getGunID(player));
    }
    
    /**
     * Updates the ItemStack's durability/lore and returns the CrackshotGunItemStack
     * @param item
     * @return CrackshotGunItemStack
     */
    private static CrackshotGunItemStack shootCrackshotGunItemStack(final ItemStack item)
    {
        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasLore())
            return null;

        final CrackshotGunItemStack csItem = new CrackshotGunItemStack(item);

        if (csItem.shoot()) {
            return csItem;
        } else {
            return null;
        }
    }
    
}
