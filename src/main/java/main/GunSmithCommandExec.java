/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

//import net.projectzombie.crackshot_enhanced.custom_weapons.GunSmithController;
//import net.projectzombie.crackshot_enhanced.custom_weapons.types.Mod.ModType;
import java.util.ArrayList;
import cs.guns.components.Attachments;
import cs.guns.components.ProjectileAttachments;
import cs.guns.components.Barrels;
import cs.guns.components.Bolts;
import cs.guns.components.FireModes;
import cs.guns.components.GunModifier;
import cs.guns.components.GunModifierType;
import static cs.guns.components.GunModifierType.*;
import cs.guns.components.Magazines;
import cs.guns.components.Sights;
import cs.guns.components.Stocks;
import cs.guns.components.ModifierLoreBuilder;
import cs.guns.weps.Guns.CrackshotGun;
import cs.guns.listeners.ShootListener;
import cs.guns.physical.components.GunModifierItemStack;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jbannon
 */
public class GunSmithCommandExec implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) 
    {
        if (!(cs instanceof Player))
            return true;
		
        final Player sender = (Player)cs;
        if (!sender.isOp())
            return true;
        
        if (args.length == 1 && args[0].equalsIgnoreCase("stats"))
        {
            final CrackshotGun gun = ShootListener.getGun(sender);
            final ArrayList<String> stats;
            
            if (gun == null)
                sender.sendMessage("You must have a gun in hand to view its stats.");
            else
            {
                stats = gun.getAllStatInfo();
                for (String str : stats)
                    sender.sendMessage(str);
            }
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("list"))
        {
            listModifierTypes(sender);
        }
        else if (args.length == 2 && args[0].equalsIgnoreCase("list"))
        {
            if (args[1].equalsIgnoreCase("attatchment1")
                    || args[1].equalsIgnoreCase("attatchment2")
                    || args[1].equalsIgnoreCase("attatchment3"))
            {
                //listModifierNames(sender, ProjectileAttatchments.getInstance().getAll());
                listModifierNames(sender, Attachments.getInstance().getAll());
            }
            else if (args[1].equalsIgnoreCase("barrel"))
                listModifierNames(sender, Barrels.getInstance().getAll());
            else if (args[1].equalsIgnoreCase("bolt"))
                listModifierNames(sender, Bolts.getInstance().getAll());
            else if (args[1].equalsIgnoreCase("firemode"))
                listModifierNames(sender, FireModes.getInstance().getAll());
            else if (args[1].equalsIgnoreCase("magazine"))
                listModifierNames(sender, Magazines.getInstance().getAll());
            else if (args[1].equalsIgnoreCase("sight"))
                listModifierNames(sender, Sights.getInstance().getAll());
            else if (args[1].equalsIgnoreCase("stock"))
                listModifierNames(sender, Stocks.getInstance().getAll());
        }
        else if (args.length == 3 && args[0].equalsIgnoreCase("get"))
        {
            int index;
            ItemStack modItem;
            try
            {
                index = Integer.valueOf(args[2]);
                if (index < 0)
                {
                    sender.sendMessage("Must specify a valid index.");
                    return true;
                }
            }
            catch (NumberFormatException ex)
            {
                sender.sendMessage("Must specify a valid index.");
                return true;
            }
            
            if (args[1].equalsIgnoreCase("attatchment1"))
                modItem = getModItem(SLOT_ONE_ATTACHMENT, index);
            else if (args[1].equalsIgnoreCase("attatchment2"))
                modItem = getModItem(SLOT_TWO_ATTATCHMENT, index);
            else if (args[1].equalsIgnoreCase("attatchment3"))
                modItem = getModItem(SLOT_THREE_ATTATCHMENT, index);
            else if (args[1].equalsIgnoreCase("barrel"))
                modItem = getModItem(BARREL, index);
            else if (args[1].equalsIgnoreCase("bolt"))
                modItem = getModItem(BOLT, index);
            else if (args[1].equalsIgnoreCase("firemode"))
                modItem = getModItem(FIREMODE, index);
            else if (args[1].equalsIgnoreCase("magazine"))
                modItem = getModItem(MAGAZINE, index);
            else if (args[1].equalsIgnoreCase("sight"))
                modItem = getModItem(SIGHT, index);
            else if (args[1].equalsIgnoreCase("stock"))
                modItem = getModItem(STOCK, index);
            else
                modItem = null;
            
            if (modItem != null)
            {
                sender.getInventory().addItem(modItem);
                sender.sendMessage("Recieved " + modItem.getItemMeta().getDisplayName());
            }
            else
            {
                sender.sendMessage(args[1] + " with index " + index + " does not exist.");
            }
        }
        else
        {
            listCommands(sender);
        }
        return true;
    }
    
    private ItemStack getModItem(final GunModifierType type,
                                 final int index)
    {
        return new GunModifierItemStack(type, index).toItem();
    }
    
    public void listCommands(final Player sender)
    {
        sender.sendMessage("/gunsmith list");
        sender.sendMessage("/gunsmith list <modifier type>");
        sender.sendMessage("/gunsmith get <modifier type> <index>");
    }
    
    public void listModifierTypes(final Player sender)
    {
        sender.sendMessage("Attatchment1");
        sender.sendMessage("Attatchment2");
        sender.sendMessage("Attatchment3");
        sender.sendMessage("Barrel");
        sender.sendMessage("Bolt");
        sender.sendMessage("FireMode");
        sender.sendMessage("Magazine");
        sender.sendMessage("Sight");
        sender.sendMessage("Stock");
    }
    
    public void listModifierNames(final Player sender,
                                  final GunModifier[] mods)
    {
        for (int i = 0; i < mods.length; i++)
        {
            sender.sendMessage(i + " " + mods[i].getName());
        }
    }
    
}
