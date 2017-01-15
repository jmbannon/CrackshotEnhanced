/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.components.modifier;

import net.projectzombie.crackshotenhanced.guns.crafting.CraftableType;
import net.projectzombie.crackshotenhanced.guns.physical.components.GunModifierItemStack;
import net.projectzombie.crackshotenhanced.guns.utilities.GunUtils;
import net.projectzombie.crackshotenhanced.yaml.ModifierValue;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;


/**
 *
 * @author jesse
 */
public abstract class GunModifier extends ModifierValue
{
    private static final String DEFAULT_COLOR = ChatColor.GREEN.toString();

    private final int price;
    private final ChatColor color;
    private final CraftableType type;
    
    public GunModifier(final int uniqueID,
                       final String name,
                       final int price,
                       final String color,
                       final CraftableType type)
    {
        super(uniqueID, name);
        this.price = price;
        this.color = GunUtils.matchChatColor(color);
        this.type = type;
    }
    
    /**
     * @return Returns the null modifier.
     */
    abstract public GunModifier getNullModifier();
    
    /**
     * @return Returns the price of the gun modification at the gunsmith.
     */
    public int price()
    {
        return price;
    }
    
    /**
     * @return Color of the GunModifier in lore based on rarity.
     */
    public ChatColor getColor()
    {
        return color;
    }
    
    /**
     * @return Whether the modifier is null.
     */
    public boolean isNull()
    {
        return this.equals(this.getNullModifier()) || super.getName() == null;
    }
    
    public String getDisplayName(final boolean italics)
    {
        final String chatColor;
        if (color == null)
            chatColor = DEFAULT_COLOR;
        else
            chatColor = color.toString();
        
        if (super.getName() != null)
        {
            if (italics)
                return chatColor + ChatColor.ITALIC.toString() + super.getName();
            else
                return chatColor + super.getName();
        }
        else
        {
            if (italics)
                return ChatColor.DARK_RED + ChatColor.ITALIC.toString() + "n/a";
            else
                return ChatColor.DARK_RED + "n/a"; 
        }
    }

    /** Converts the gun modifier to an ItemStack. */
    public ItemStack toItemStack() { return new GunModifierItemStack(this.type, this.getIndex()).toItemStack(); }
}
