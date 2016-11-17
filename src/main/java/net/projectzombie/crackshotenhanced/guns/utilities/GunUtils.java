/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshotenhanced.guns.utilities;

import java.util.ArrayList;
import net.projectzombie.crackshotenhanced.guns.components.modifier.GunModifier;
import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 *
 * @author jbannon
 */
public class GunUtils
{
    /**
     * Sorts GunModifiers by price ascending using bubble sort.
     * @param mods ArrayList of GunModifier
     * @return Sorted ArraylList of GunModifier
     */
    static
    public ArrayList<GunModifier> sortMods(final ArrayList<GunModifier> mods)
    {
        GunModifier temp;
        int i = 0;
        while (i < mods.size() - 1)
        {
            if (mods.get(i).price() > mods.get(i + 1).price())
            {
                temp = mods.get(i);
                mods.set(i, mods.get(i + 1));
                mods.set(i + 1, temp);
                i = 0;
            }
            else
            {
                ++i;
            }
        }
        return mods;
    }
    
    /**
     * Attempts to match a string with a ChatColor enum.
     * @param string Name of ChatColor.
     * @return ChatColor if matches. Null otherwise.
     */
    static
    public ChatColor matchChatColor(final String string)
    {
        if (string == null)
            return null;
        
        ChatColor colors[] = ChatColor.values();
        for (ChatColor color : colors)
        { 
            if (color.name().equalsIgnoreCase(string))
            {
                return color;
            }
        }
        return null;
    }
    
    /**
     * Attempts to match a string with a Material enum.
     * @param string Name of Material.
     * @return Material if matches. Null otherwise.
     */
    static
    public Material matchMaterial(final String string)
    {
        if (string == null)
            return null;
        
        return Material.matchMaterial(string);
    }
}
