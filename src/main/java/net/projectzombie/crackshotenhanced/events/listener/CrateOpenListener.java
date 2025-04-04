package net.projectzombie.crackshotenhanced.events.listener;

import net.projectzombie.crackshotenhanced.guns.physical.crate.GunCrateItemStack;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * Created by jb on 1/20/17.
 */
public class CrateOpenListener implements Listener {

    static public boolean isRightClick(PlayerInteractEvent event) {
        final Action action = event.getAction();
        return action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK);
    }

    /**
     * TODO: Open the crate using crafting
     *
     * Opens the crate when it's in the player's main hand with a right-click event.
     * @param event Inventory click on a crate.
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void CrateOpenListener(PlayerInteractEvent event)
    {
        if (!isRightClick(event))
            return;

        final PlayerInventory inv = event.getPlayer().getInventory();
        final ItemStack item = inv.getItemInMainHand();
        final GunCrateItemStack gunCrate = new GunCrateItemStack(item);
        if (gunCrate.isValid()) {
            if (item.getAmount() > 1) {
                event.getPlayer().sendMessage("Can only open one crate at a time.");
            } else {
                inv.setItemInMainHand(gunCrate.openCrate());
            }
        }
    }
}
