package net.projectzombie.crackshotenhanced.guns.events.listener;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.shampaggon.crackshot.events.WeaponScopeEvent;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.PlayerInventory;

/**
 * 
 * @author Jesse Bannon (jmbannon@uw.edu)
 * 
 * Listens for a scope event from CrackShot guns and places a pumpkin on
 * the player's head to replicate a scope. The player's helmet is saved to a
 * hash map of players and is restored when the player zooms out or when the
 * server is restarted.
 */
public class ScopeZoomListener implements Listener
{
    private static final HashMap<UUID, ItemStack> helmetList = new HashMap<>();

    /**
     * Places pumpkin on the player's head when zoomed in. Restores helmet
     * when the player zooms out.
     * 
     * @param event CrackShot weapon scope event.
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void scopeEvent(WeaponScopeEvent event)
    {
        final Player player = event.getPlayer();
        final UUID playerUUID = event.getPlayer().getUniqueId();
        final ItemStack scope = new ItemStack(Material.PUMPKIN);

        if (event.isZoomIn())
        {
            helmetList.put(playerUUID, player.getInventory().getHelmet());	
            player.getInventory().setHelmet(scope);
        } 
        else if (helmetList.containsKey(playerUUID))
        {
            player.getInventory().setHelmet(helmetList.get(playerUUID));
            helmetList.remove(playerUUID);
        }
    }
    
    /**
     * Removes the pumpkin if the player attempts to take it off their head
     * while scoped.
     * 
     * @param event Inventory click on the pumpkin. 
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void RemoveScopeOnClick(InventoryClickEvent event)
    {
        final int slot = event.getSlot();
        final PlayerInventory inv = event.getWhoClicked().getInventory();
        if (slot < 0 || slot >= inv.getSize())
            return;
        
        final ItemStack clickedItem = inv.getItem(slot);
        if (clickedItem != null && clickedItem.getType().equals(Material.PUMPKIN))
            inv.remove(clickedItem);
    }
	
    /**
     * To be ran on disable. Restores any helmets if someone is scoped while
     * the server is disabled.
     */
    public void disable()
    {
        Player player;
        if (!helmetList.isEmpty())
        {
            for (UUID playerUUID : helmetList.keySet())
            {
                player = Bukkit.getPlayer(playerUUID);
                if (player != null && player.isOnline())
                {
                    player.getInventory().setHelmet(helmetList.get(playerUUID));
                }
            }
        }
    }
	
}
