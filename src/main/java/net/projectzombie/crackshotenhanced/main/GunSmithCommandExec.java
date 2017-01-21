/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.main;

import java.util.ArrayList;

import net.projectzombie.crackshotenhanced.guns.components.modifier.*;
import net.projectzombie.crackshotenhanced.guns.components.skeleton.GunSkeletons;
import net.projectzombie.crackshotenhanced.guns.crafting.CraftableType;
import net.projectzombie.crackshotenhanced.guns.physical.crate.GunCrateItemStack;
import net.projectzombie.crackshotenhanced.guns.physical.modifier.GunModifierItemStack;
import net.projectzombie.crackshotenhanced.guns.gun.CrackshotGun;
import net.projectzombie.crackshotenhanced.events.listener.ShootListener;
import net.projectzombie.crackshotenhanced.guns.qualities.Qualities;
import net.projectzombie.crackshotenhanced.yaml.ModifierValue;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static net.projectzombie.crackshotenhanced.guns.crafting.CraftableType.*;

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
                gun.getStats().forEach(sender::sendMessage);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("components")) {
            final CrackshotGun gun = ShootListener.getGun(sender);
            if (gun == null)
                sender.sendMessage("You must have a gun in hand to view its stats.");
            else
                gun.getComponentStats().forEach(sender::sendMessage);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
            listModifierTypes(sender);
        } else if (args.length == 2 && args[0].equalsIgnoreCase("list")) {
            switch(args[1]) {
                case "attachment1": listModifierNames(sender, ProjectileAttachments.getSlotOneInstance().getAll()); break;
                case "attachment2": listModifierNames(sender, ProjectileAttachments.getSlotTwoInstance().getAll()); break;
                case "attachment3": listModifierNames(sender, ProjectileAttachments.getSlotThreeInstance().getAll()); break;
                case "barrel": listModifierNames(sender, Barrels.getInstance().getAll()); break;
                case "bolt": listModifierNames(sender, Bolts.getInstance().getAll()); break;
                case "firemode": listModifierNames(sender, FireModes.getInstance().getAll()); break;
                case "magazine": listModifierNames(sender, Magazines.getInstance().getAll()); break;
                case "sight": listModifierNames(sender, Sights.getInstance().getAll()); break;
                case "stock": listModifierNames(sender, Stocks.getInstance().getAll()); break;
                case "skeleton": listModifierNames(sender, GunSkeletons.getInstance().getAll()); break;
                case "quality": listModifierNames(sender, Qualities.getInstance().getAll()); break;
                default: break;
            }
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

            switch(args[1]) {
                case "attachment1": modItem = getModItem(SLOT_ONE_ATTACHMENT, index); break;
                case "attachment2": modItem = getModItem(SLOT_TWO_ATTACHMENT, index); break;
                case "attachment3": modItem = getModItem(SLOT_THREE_ATTACHMENT, index); break;
                case "barrel": modItem = getModItem(BARREL, index);break;
                case "bolt": modItem = getModItem(BOLT, index); break;
                case "firemode": modItem = getModItem(FIREMODE, index); break;
                case "magazine": modItem = getModItem(MAGAZINE, index); break;
                case "sight": modItem = getModItem(SIGHT, index); break;
                case "stock": modItem = getModItem(STOCK, index); break;
                case "skeleton": modItem = getModItem(SKELETON, index); break;
                case "quality": modItem = Qualities.getInstance().get(index).toItemStack(); break;
                default: modItem = null;
            }
            
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
    
    private ItemStack getModItem(final CraftableType type,
                                 final int index)
    {
        return new GunModifierItemStack(type, index).toItemStack();
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
        sender.sendMessage("Skeleton");
        sender.sendMessage("Quality");
    }

    public void listModifierNames(final Player sender,
                                  final ArrayList<? extends ModifierValue> modifierValues)
    {
        for (int i = 0; i < modifierValues.size(); i++) {
            sender.sendMessage(i + " " + modifierValues.get(i).getName());
        }
    }
    
}
