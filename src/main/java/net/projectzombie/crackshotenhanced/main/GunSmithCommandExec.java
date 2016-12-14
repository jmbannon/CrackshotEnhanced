/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.main;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.guns.components.modifier.*;
import net.projectzombie.crackshotenhanced.guns.crafting.GunModifierType;
import net.projectzombie.crackshotenhanced.guns.physical.components.GunModifierItemStack;
import net.projectzombie.crackshotenhanced.guns.weps.CrackshotGun;
import net.projectzombie.crackshotenhanced.events.listener.ShootListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static net.projectzombie.crackshotenhanced.guns.crafting.GunModifierType.*;

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
        
        if (args.length == 1 && args[0].equalsIgnoreCase("stats"))  {
            final CrackshotGun gun = ShootListener.getGun(sender);
            if (gun == null)
                sender.sendMessage("You must have a gun in hand to view its stats.");
            else
                gun.getStats().forEach(s -> sender.sendMessage(s));
        } else if (args.length == 1 && args[0].equalsIgnoreCase("components")) {
            final CrackshotGun gun = ShootListener.getGun(sender);
            if (gun == null)
                sender.sendMessage("You must have a gun in hand to view its stats.");
            else
                gun.getComponentStats().forEach(s -> sender.sendMessage(s));
        } else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
            listModifierTypes(sender);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("list")) {
            if (args[1].equalsIgnoreCase("attachment1")
                    || args[1].equalsIgnoreCase("attachment2")
                    || args[1].equalsIgnoreCase("attachment3"))
                listModifierNames(sender, ProjectileAttachments.getInstance().getAll());
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
            
            if (args[1].equalsIgnoreCase("attachment1"))
                modItem = getModItem(SLOT_ONE_ATTACHMENT, index);
            else if (args[1].equalsIgnoreCase("attachment2"))
                modItem = getModItem(SLOT_TWO_ATTATCHMENT, index);
            else if (args[1].equalsIgnoreCase("attachment3"))
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
        sender.sendMessage("Attachment1");
        sender.sendMessage("Attachment2");
        sender.sendMessage("Attachment3");
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

    public void listModifierNames(final Player sender,
                                  final ArrayList<? extends GunModifier> mods)
    {
        for (int i = 0; i < mods.size(); i++)
        {
            sender.sendMessage(i + " " + mods.get(i).getName());
        }
    }
    
}
